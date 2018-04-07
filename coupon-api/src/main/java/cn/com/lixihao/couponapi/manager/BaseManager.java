package cn.com.lixihao.couponapi.manager;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;

/**
 * Created by haoweige on 2017/8/25.
 */
public class BaseManager extends SqlSessionDaoSupport {

    //@Resource(name = "jdbcTemplate")
    //@Override
    //public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    //    super.setSqlSessionTemplate(sqlSessionTemplate);
    //}
}
