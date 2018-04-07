package cn.com.lixihao.couponapi.service;

import cn.com.lixihao.couponapi.api.YougouApi;
import cn.com.lixihao.couponapi.entity.result.PageResponse;
import cn.com.lixihao.couponapi.entity.result.UnifiedResponse;
import cn.com.lixihao.couponapi.entity.result.YougouGoods.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class YougouService {

    @Autowired
    private YougouApi yougouApi;

    public List<GoodsCategoryResponse> getCategory(GoodsCategoryRequest goodsCategoryRequest) {
        String getTopUrl = "/api/open/domyshop/app/getTopCategory";
        String getSubUrl = "/api/open/domyshop/app/getSubCategory";
        String url;
        if (StringUtils.isBlank(goodsCategoryRequest.getParentSn())) {
            url = getTopUrl;
        } else {
            url = getSubUrl;
        }
        JSONObject dataObject = JSONObject.parseObject(getData(url, goodsCategoryRequest).toString());
        int totalCount = dataObject.getInteger("totalCount");
        goodsCategoryRequest.setPageSize(totalCount);//利用第一次请求返回的totalcount来请求所有的goods
        dataObject = JSONObject.parseObject(this.getData(url, goodsCategoryRequest).toString());
        JSONArray records = dataObject.getJSONArray("records");
        List<GoodsCategoryResponse> list = records.toJavaList(GoodsCategoryResponse.class);
        return list;
    }

    public PageResponse getGoodsSku(GoodsCategoryRequest goodsCategoryRequest) {
        List<Goods> goodsDetails = this.getGoodsDetails(goodsCategoryRequest);
        List skuList = this.getSku(goodsDetails);
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        if (skuList.size() > 0) {
            pageResponse.setRows(skuList);
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
        }
        return pageResponse;
    }

    public PageResponse getGoodsBysku(String skus) {
        String url = "/api/open/domyshop/app/getCategoryBySku";
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> invalidSkuList = new ArrayList<String>();
        List<Object> list = new ArrayList<Object>();
        for (String sku : skus.split(",")) {
            param.put("goodsSkuSn", sku);
            JSONArray levelList;
            try {
                levelList = JSONArray.parseArray(this.getData(url, param).toString());
                if (levelList.isEmpty()) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                invalidSkuList.add(sku); //此sku为无效值，将返回无效sku列表
                continue;
            }
            String categorySn = ((JSONObject) levelList.get(2)).getString("categorySn");
            GoodsCategoryRequest category = new GoodsCategoryRequest();
            category.setCategorySn(categorySn);
            PageResponse tmpResponse = this.getGoodsSku(category);
            for (Object goodsSkuResponse : tmpResponse.getRows()) {
                if (StringUtils.equals(((GoodsSkuResponse) goodsSkuResponse).getGoodskuSn(), sku)) {
                    list.add(goodsSkuResponse);
                }
            }
        }
        PageResponse pageResponse = new PageResponse();
        if (invalidSkuList.size() > 0) {
            pageResponse.setRows(invalidSkuList);
            pageResponse.setReturn_code(UnifiedResponse.FAIL);
            pageResponse.setReturn_value("INVALID_SKU_SN");
        } else {
            if (list.size() >= 1) {
                pageResponse.setRows(list);
                pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
                pageResponse.setReturn_value("ok");
            } else {
                throw new RuntimeException("NOT_FOUND!");
            }
        }
        return pageResponse;
    }

    public PageResponse checkCategorySn(String categorySns, Integer type) {
        String url = "/api/open/domyshop/app/getGoodsInfoBySn";
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "INVALID_CATEGORY_SN");
        List<String> invalidList = new ArrayList<String>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", type);
        params.put("categorySn", categorySns);
        JSONArray jsonArray = JSON.parseArray(this.getData(url, params).toString());
        List<GoodsCategoryResponse> list = jsonArray.toJavaList(GoodsCategoryResponse.class);
        int snLength = categorySns.split(",").length;
        if (snLength == list.size()) {
            pageResponse.setRows(list);
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
        } else {
            for (String categorySn : categorySns.split(",")) {
                boolean flag = false;
                for (GoodsCategoryResponse goodsCategoryResponse : list) {
                    if (categorySn.equals(goodsCategoryResponse.getCategorySn())) {
                        flag = true;
                    }
                }
                if (!flag) {
                    invalidList.add(categorySn);
                }
            }
            pageResponse.setRows(invalidList);
        }
        return pageResponse;
    }

    public PageResponse searchName(String name) {
        String url = "/api/open/domyshop/app/getSearchGoodsByName";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("goodsName", name);
        params.put("pageIndex", 1);
        params.put("pageSize", 1);
        JSONObject dataObject = JSONObject.parseObject(this.getData(url, params).toString());
        params.put("pageSize", dataObject.getInteger("totalCount"));        //利用第一次请求返回的totalcount来请求所有的goods
        dataObject = JSONObject.parseObject(this.getData(url, params).toString());
        List jsonArray = dataObject.getJSONArray("records");
        List<Goods> list = new ArrayList<Goods>();
        for (Object goodsObj : jsonArray) {
            JSONObject goodsJson = (JSONObject) goodsObj;
            Goods goods = goodsJson.toJavaObject(Goods.class);
            list.add(goods);
        }
        List<GoodsSkuResponse> skuList = this.getSku(list);
        PageResponse pageResponse = new PageResponse(UnifiedResponse.FAIL, "NOT_FOUND!");
        if (skuList.size() > 0) {
            pageResponse.setRows(skuList);
            pageResponse.setReturn_code(UnifiedResponse.SUCCESS);
            pageResponse.setReturn_value("ok");
        }
        return pageResponse;
    }

    private List<GoodsSkuResponse> getSku(List<Goods> goodsList) {
        String url = "/api/open/domyshop/app/getSkuByCategory";
        List skuList = new ArrayList();
        for (Goods goods : goodsList) {
            GoodsSkuRequest goodsSkuRequest = new GoodsSkuRequest();
            String category = goods.getCategoryTreePath().split(",")[2];
            goodsSkuRequest.setCategorySn(category);
            goodsSkuRequest.setGoodsName(goods.getName());
            goodsSkuRequest.setMarketStatus(1);
            goodsSkuRequest.setMinPrice(0.00);
            goodsSkuRequest.setMaxPrice(goods.getMarketPrice());
            goodsSkuRequest.setPageSize(50);
            JSONObject dataObj;
            try {
                dataObj = JSONObject.parseObject(this.getData(url, goodsSkuRequest).toString());
            } catch (Exception e) {
                continue;
            }
            List skuJsonArray = dataObj.getJSONArray("records");
            for (Object skuObj : skuJsonArray) {
                GoodsSkuResponse response = JSONObject.parseObject(skuObj.toString()).toJavaObject(GoodsSkuResponse.class);
                response.setMarketPrice(goods.getMarketPrice());
                response.setPrice(goods.getPrice());
                skuList.add(response);
            }
        }
        return skuList;
    }

    private List<Goods> getGoodsDetails(GoodsCategoryRequest goodsCategoryRequest) {
        String goodsUrl = "/api/open/domyshop/app/getGoodsByCategory";
        JSONObject dataObject = (JSONObject) this.getData(goodsUrl, goodsCategoryRequest);
        int totalCount = dataObject.getInteger("totalCount");
        goodsCategoryRequest.setPageSize(totalCount);
        dataObject = JSONObject.parseObject(this.getData(goodsUrl, goodsCategoryRequest).toString());
        List jsonArray = dataObject.getJSONArray("records");
        List<Goods> list = new ArrayList<Goods>();
        for (Object goodsObj : jsonArray) {
            JSONObject goodsJson = (JSONObject) goodsObj;
            Goods goods = goodsJson.toJavaObject(Goods.class);
            list.add(goods);
        }
        return list;
    }

    private Object getData(String url, Object obj) {
        String json = yougouApi.get(obj, url);
        JSONObject root = JSONObject.parseObject(json);
        String returnValue = root.getString("returnValue");
        String errorMessage = root.getString("errorMessage");
        if (StringUtils.equals("0", returnValue)) {
            return root.get("data");
        } else {
            throw new RuntimeException(errorMessage);
        }
    }

}
