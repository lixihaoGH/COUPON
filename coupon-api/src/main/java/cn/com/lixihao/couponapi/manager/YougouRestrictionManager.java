package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.YougouRestrictionCondition;
import cn.com.lixihao.couponapi.mapper.YougouRestrictionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YougouRestrictionManager extends BaseManager {

    @Autowired
    private YougouRestrictionMapper mapper;

    public YougouRestrictionCondition get(YougouRestrictionCondition condition) {
        return mapper.get(condition);
    }

    public Integer insert(YougouRestrictionCondition condition) {
        return mapper.insert(condition);
    }

    public Integer delete(YougouRestrictionCondition condition) {
        return mapper.delete(condition);
    }

    public Integer update(YougouRestrictionCondition condition) {
        return mapper.update(condition);
    }

    public YougouRestrictionCondition query(YougouRestrictionCondition condition) {
        return mapper.query(condition);
    }

}
