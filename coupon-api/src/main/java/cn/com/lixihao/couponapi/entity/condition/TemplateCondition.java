package cn.com.lixihao.couponapi.entity.condition;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
public class TemplateCondition implements Serializable {
    private static final long serialVersionUID = 1L;
    public Integer id;
    public String page_title;
    public String release_id;
    public String page_img_url;
    public String goto_page_url;
    public String page_bgcolor;
    public String share_title;
    public String share_img_url;
    public String share_desc;


    public TemplateCondition(Integer defaultCode) {
        this.page_title = "大麦盒子，最个性化的机顶盒";
        this.page_bgcolor = "#483D8B";
        this.page_img_url = "http://test.web.weixin.pthv.gitv.tv/hiveview-weixinweb/res/image/coupon/background.jpg";
        this.goto_page_url = "www.hiveview.com";
        this.release_id = "0";
        this.share_title = "大麦优购，乐享精致生活";
        this.share_desc = "福利送不停，快来领取缤纷红包！";
        this.share_img_url = "http://test.web.weixin.pthv.gitv.tv/hiveview-weixinweb/res/image/coupon/thumbnail.jpg";
    }

    public TemplateCondition() {
    }
}
