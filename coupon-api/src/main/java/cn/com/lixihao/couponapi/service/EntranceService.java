package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.constants.ApiConstants;
import cn.com.lixihao.couponapi.constants.SysConstants;
import cn.com.lixihao.couponapi.dao.EntranceDao;
import cn.com.lixihao.couponapi.entity.condition.EntranceCondition;
import cn.com.lixihao.couponapi.entity.result.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntranceService {

    @Autowired
    private EntranceDao entranceDao;
    @Autowired
    private ReleaseService releaseService;
    @Autowired
    ShardedJedisPool shardedJedisPool;

    public EntranceResponse get(EntranceCondition condition) {
        return entranceDao.get(condition);
    }

    public String entrance(EntranceCondition condition) {
        String key = "coupon_entrance_" + condition.id;
        ShardedJedis shardedJedis = null;
        try {
            String release_id_list = "";
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                release_id_list = shardedJedis.get(key);
            } else {
                EntranceResponse entranceResponse = entranceDao.get(condition);
                if (entranceResponse == null) {
                    throw new RuntimeException("NOT_FOUND");
                }
                release_id_list = entranceResponse.release_id_list;
            }
            shardedJedis.set(key, release_id_list);
            shardedJedis.expire(key, 360);
            String release_id = this.sortAndVerify(release_id_list);
            return release_id;
        } catch (Exception e) {
            throw new RuntimeException("NOT_FOUND");
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }

    }

    public PageResponse getList(EntranceCondition condition) {
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        List<EntranceResponse> list = entranceDao.getList(condition);
        Integer total = entranceDao.getCount(condition);
        if (list.size() > 0) {
            List<EntranceResponse> rows = new ArrayList<EntranceResponse>();
            for (EntranceResponse entranceResponse : list) {
                String goto_url = ApiConstants.COUPON_API_URL + "entrance/" + entranceResponse.getId();
                entranceResponse.setGoto_url(goto_url);
                rows.add(entranceResponse);
            }
            pageResponse.setRows(rows);
            pageResponse.setTotal(total);
            pageResponse.setReturn_value("ok");
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
        }
        return pageResponse;
    }

    public UnifiedResponse save(EntranceCondition condition) {
        String time = DateTime.now().toString(SysConstants.DATE_FORMAT);
        condition.update_time = time;
        condition.create_time = time;
        Integer result = entranceDao.insert(condition);
        if (result != 1) {
            throw new RuntimeException("创建入口失败!");
        }
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "ok");
    }

    public UnifiedResponse update(EntranceCondition condition) {
        String update_time = DateTime.now().toString(SysConstants.DATE_FORMAT);
        condition.setUpdate_time(update_time);
        Integer result = entranceDao.update(condition);
        if (result == 0) {
            throw new RuntimeException("更新的入口不存在或数据有误!");
        }
        String key = "coupon_entrance_" + condition.id;
        this.delCache(key);
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "ok");
    }

    public UnifiedResponse delete(EntranceCondition condition) {
        Integer result = entranceDao.delete(condition);
        if (result == 0) {
            throw new RuntimeException("删除的入口不存在或数据有误!");
        }
        String key = "coupon_entrance_" + condition.id;
        this.delCache(key);
        return new UnifiedResponse(UnifiedResponse.SUCCESS, "ok");
    }

    private void delCache(String key) {
        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis.exists(key)) {
                shardedJedis.del(key);
            }
        } catch (Exception e) {
            throw new RuntimeException("NOT_FOUND");
        } finally {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    private String sortAndVerify(String release_id_list) {
        if (release_id_list.split(",").length == 1) {
            String release_id = release_id_list.split(",")[0];
            Integer release_status = releaseService.releaseStatus(release_id);
            if (release_status == ReleaseStatusType.EIIECTIVE) {
                return release_id;
            }
        }
        PageResponse pageResponse = releaseService.getList(release_id_list);
        List<ReleaseResponse> list = pageResponse.getRows();
        ReleaseResponse[] releaseArray = new ReleaseResponse[list.size()];
        list.toArray(releaseArray);
        DateTimeFormatter dtf = DateTimeFormat.forPattern(SysConstants.DATE_FORMAT);
        for (int i = 0; i < releaseArray.length; i++) {
            for (int j = 1; j < releaseArray.length; j++) {
                DateTime dateTimei = DateTime.parse(releaseArray[i].release_start_time, dtf);
                DateTime dateTimej = DateTime.parse(releaseArray[j].release_start_time, dtf);
                if (dateTimei.compareTo(dateTimej) >= 0) {
                    ReleaseResponse temp = releaseArray[i];
                    releaseArray[i] = releaseArray[j];
                    releaseArray[j] = temp;
                }
            }
        }
        for (ReleaseResponse release : releaseArray) {
            if (!StringUtils.isBlank(release.release_id)) {
                Integer release_status = releaseService.releaseStatus(release.release_id);
                if (release_status == ReleaseStatusType.EIIECTIVE) {
                    return release.release_id;
                }
            }
        }
        return "0";
    }
}
