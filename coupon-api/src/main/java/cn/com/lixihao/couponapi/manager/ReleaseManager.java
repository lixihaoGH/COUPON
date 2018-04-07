package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.ReleaseConditon;
import cn.com.lixihao.couponapi.entity.result.ReleaseResponse;
import cn.com.lixihao.couponapi.mapper.ReleaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseManager extends BaseManager {

    @Autowired
    private ReleaseMapper releaseMapper;

    public ReleaseResponse get(ReleaseConditon conditon) {
        return releaseMapper.get(conditon);
    }

    public List<ReleaseResponse> getList(ReleaseConditon conditon) {
        return releaseMapper.getList(conditon);
    }

    public Integer insert(ReleaseConditon conditon) {
        return releaseMapper.insert(conditon);
    }

    public Integer update(ReleaseConditon conditon) {
        return releaseMapper.update(conditon);
    }

    public Integer delete(ReleaseConditon conditon) {
        return releaseMapper.delete(conditon);
    }

    public Integer getCount(ReleaseConditon conditon) {
        Integer result = releaseMapper.getCount(conditon);
        if (result == null) {
            result = 0;
        }
        return result;
    }
}
