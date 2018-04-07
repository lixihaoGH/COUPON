package cn.com.lixihao.couponapi.controller;

import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.entity.result.YougouGoods.GoodsCategoryRequest;
import cn.com.lixihao.couponapi.service.YougouService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RequestMapping(value = "/yougou")
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class YougouGoodsController {

    private Logger DATA = LoggerFactory.getLogger(YougouGoodsController.class);

    @Autowired
    private YougouService yougouService;

    @ResponseBody
    @RequestMapping(value = "/getCategory", method = RequestMethod.GET)
    public List getCategory(GoodsCategoryRequest goodsCategoryRequest) {
        DATA.info("[yougou]getCategory: parentSn->{}", goodsCategoryRequest.getParentSn());
        List pageResponse;
        try {
            pageResponse = yougouService.getCategory(goodsCategoryRequest);
        } catch (Exception e) {
            DATA.error("[yougou]getCategory: exception->{}", goodsCategoryRequest + e.getMessage());
            pageResponse = new ArrayList();
        }
        DATA.info("[yougou]getCategory: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getGoodsSku", method = RequestMethod.GET)
    public PageResponse getGoodsSku(GoodsCategoryRequest goodsCategoryRequest) {
        DATA.info("[yougou]getGoodsSku: categorySn->{}", goodsCategoryRequest.getCategorySn());
        PageResponse pageResponse;
        try {
            pageResponse = yougouService.getGoodsSku(goodsCategoryRequest);
        } catch (Exception e) {
            DATA.error("[yougou]getGoodsSku: exception->{}", goodsCategoryRequest + "," + e.getMessage());
            pageResponse = new PageResponse(UnifiedResponse.FAIL, e.getMessage());
        }
        DATA.info("[yougou]getGoodsSku: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageResponse getGoodsByName(String name) {
        DATA.info("[yougou]search: name->{}", name);
        PageResponse pageResponse;
        try {
            pageResponse = yougouService.searchName(name);
        } catch (Exception e) {
            DATA.error("[yougou]search: exception->{}", e.getMessage());
            pageResponse = new PageResponse(UnifiedResponse.FAIL, e.getMessage());
        }
        DATA.info("[yougou]search: response->{}", JSON.toJSONString(pageResponse));
        return pageResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public PageResponse check(@RequestParam(value = "type", defaultValue = "-1") Integer type, String snList) {
        DATA.info("[yougou]check: type->{},snList->{}", type, snList);
        PageResponse pageResponse;
        try {
            if (type == -1) {
                pageResponse = yougouService.getGoodsBysku(snList);
            } else {
                pageResponse = yougouService.checkCategorySn(snList, type);
            }
        } catch (Exception e) {
            DATA.error("[yougou]check: exception->{}", e.getMessage());
            pageResponse = new PageResponse(UnifiedResponse.FAIL, e.getMessage());
        }
        DATA.info("[yougou]check: response->{}", JSONObject.toJSONString(pageResponse));
        return pageResponse;
    }
}
