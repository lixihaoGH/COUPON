package cn.com.lixihao.couponapi.dao;

import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("templateDao")
public class TemplateDao extends BaseDao {

    @Autowired
    TemplateDao templateDao;

    public TemplateCondition get(TemplateCondition templateCondition) {
        return templateDao.get(templateCondition);
    }

/*    public List<TemplateCondition> getList(TemplateCondition templateCondition) {
        return templateMapper.getList(templateCondition);
    }*/

    public Integer insert(TemplateCondition templateCondition) {
        return templateDao.insert(templateCondition);
    }

    public Integer delete(TemplateCondition templateCondition) {
        return templateDao.delete(templateCondition);
    }

    public Integer update(TemplateCondition templateCondition) {
        return templateDao.update(templateCondition);
    }


}
