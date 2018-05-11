package cn.com.lixihao.couponapi.test.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * create by lixihao on 2018/5/10.
 **/

//@ActiveProfiles("dev")
@ContextConfiguration(locations = {"classpath*:context/*.xml"})
public class BaseTest extends AbstractTestNGSpringContextTests {
}

