package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.result.ReleaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("releaseDao")
public class ReleaseDao extends BaseDao {

    @Autowired
    ReleaseDao releaseDao;

    public ReleaseResponse get(ReleaseConditon conditon) {
        return releaseDao.get(conditon);
    }

    public List<ReleaseResponse> getList(ReleaseConditon conditon) {
        return releaseDao.getList(conditon);
    }

    public Integer insert(ReleaseConditon conditon) {
        return releaseDao.insert(conditon);
    }

    public Integer update(ReleaseConditon conditon) {
        return releaseDao.update(conditon);
    }

    public Integer delete(ReleaseConditon conditon) {
        return releaseDao.delete(conditon);
    }

    public Integer getCount(ReleaseConditon conditon) {
        Integer result = releaseDao.getCount(conditon);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
