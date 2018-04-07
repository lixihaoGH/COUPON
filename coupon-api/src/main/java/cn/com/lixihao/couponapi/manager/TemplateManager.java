package cn.com.lixihao.couponapi.manager;

import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import cn.com.lixihao.couponapi.mapper.TemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class TemplateManager extends BaseManager {

    @Autowired
    private TemplateMapper templateMapper;

    public TemplateCondition get(TemplateCondition templateCondition) {
        return templateMapper.get(templateCondition);
    }

/*    public List<TemplateCondition> getList(TemplateCondition templateCondition) {
        return templateMapper.getList(templateCondition);
    }*/

    public Integer insert(TemplateCondition templateCondition) {
        return templateMapper.insert(templateCondition);
    }

    public Integer delete(TemplateCondition templateCondition) {
        return templateMapper.delete(templateCondition);
    }

    public Integer update(TemplateCondition templateCondition) {
        return templateMapper.update(templateCondition);
    }


}
