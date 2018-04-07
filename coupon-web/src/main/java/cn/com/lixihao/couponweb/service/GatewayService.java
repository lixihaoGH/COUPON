package cn.com.lixihao.couponweb.service;

import cn.com.lixihao.couponweb.constant.SysConstants;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by lixihao on 2018/3/22.
 **/
@Service
public class GatewayService {

    public String verifyWXToken(String signature, String timestamp, String nonce, String echostr) {
        String[] args = new String[]{timestamp, nonce, SysConstants.WX_GATEWAY_TOKEN};
        String sortStr = this.sortString(args);
        if (signature.equalsIgnoreCase(DigestUtils.sha1Hex(sortStr))) {
            return echostr;
        }
        return "";
    }

    private String sortString(String[] args) {
        List<String> strs = new ArrayList<String>();
        for (String str : args) {
            strs.add(str);
        }
        Collections.sort(strs);
        StringBuffer sortStr = new StringBuffer();
        for (String str : strs) {
            sortStr.append(str);
        }
        return sortStr.toString();
    }


    public static void main(String []args){
        String[] sadas = new String[]{"1521686565", "scccc", SysConstants.WX_GATEWAY_TOKEN};
        GatewayService gatewayService = new GatewayService();
        String str = gatewayService.sortString(sadas);
        System.out.println(DigestUtils.sha1Hex(str));
    }
}
