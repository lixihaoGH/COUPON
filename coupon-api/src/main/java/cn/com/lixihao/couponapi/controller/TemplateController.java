package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.condition.TemplateCondition;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.service.TemplateService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/template")
@Controller
public class TemplateController {

    private Logger DATA = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;

    @ResponseBody
    @RequestMapping(value = "/get")
    public String get(String release_id) {
        DATA.info("[template]get: release_id->{}", release_id);
        String response;
        try {
            TemplateCondition condition = new TemplateCondition();
            condition.setRelease_id(release_id);
            response = templateService.get(condition);
        } catch (Exception e) {
            DATA.error("[template]get: exception->{}", e.getMessage());
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("return_code", UnifiedResponse.FAIL);
            jsonResponse.put("return_value", "NOT_FOUND!");
            response = jsonResponse.toJSONString();
        }
        DATA.info("[template]get: response->{}", response);
        return response;
    }
}
