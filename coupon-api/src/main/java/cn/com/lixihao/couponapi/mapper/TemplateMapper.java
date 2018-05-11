package cn.com.lixihao.couponapi.mapper;

import cn.com.lixihao.couponapi.core.mybatis.SqlMapper;
import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;

import java.util.List;

@SqlMapper
public interface TemplateMapper {
    TemplateCondition get(TemplateCondition templateCondition);

    List<TemplateCondition> getList(TemplateCondition templateCondition);

    Integer insert(TemplateCondition templateCondition);

    Integer delete(TemplateCondition templateCondition);

    Integer update(TemplateCondition templateCondition);

}
