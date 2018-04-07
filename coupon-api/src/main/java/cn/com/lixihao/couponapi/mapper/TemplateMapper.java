package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateMapper {
    TemplateCondition get(TemplateCondition templateCondition);

    List<TemplateCondition> getList(TemplateCondition templateCondition);

    Integer insert(TemplateCondition templateCondition);

    Integer delete(TemplateCondition templateCondition);

    Integer update(TemplateCondition templateCondition);

}
