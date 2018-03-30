package cn.goldrock.service;

import cn.goldrock.entity.AccessToken;
import cn.goldrock.utils.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by bluers on 2018/3/29.
 */
@Component
public class AccessService {

    private static AccessService accessService = new AccessService();

    private static Logger logger = LoggerFactory.getLogger(AccessService.class);

    private static String HOST = "https://api.weixin.qq.com";

    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    private AccessToken accessToken;

    public AccessService() {
    }
    public static AccessService getInstance(){
        if(null == accessService){
            accessService = new AccessService();
        }
        return accessService;
    }

    public AccessService getAccessService() {
        return accessService;
    }

    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }



    public AccessToken getAccessToken(String appId, String appSecret, String nonceStr) {
        JSONObject jsonObject = new JSONObject();
        AccessToken accessToken = new AccessToken(appId, appSecret, nonceStr);
        String result = "";

        if (null == accessToken.getAccessToken() || accessToken.getAccessTokenExpiresIn() * 1000 + accessToken.getAccessTokenCreateTime() > new Date().getTime()) {
            result = HttpUtils.getUrl(HOST + "/cgi-bin/token?grant_type=client_credential&appid=" + appId + "&secret=" + appSecret);
            jsonObject = JSON.parseObject(result);

            if (null != jsonObject.get("access_token")) {
                accessToken.setAccessToken(jsonObject.get("access_token").toString());
                accessToken.setAccessTokenCreateTime(new Date().getTime());
                result = HttpUtils.getUrl(HOST + "/cgi-bin/ticket/getticket?access_token=" + accessToken.getAccessToken() + "&type=jsapi");
                jsonObject = JSON.parseObject(result);
                if (null != jsonObject.get("errcode") && Integer.parseInt(jsonObject.get("errcode").toString()) == 0) {
                    accessToken.setJsapiTicket(jsonObject.get("ticket").toString());
                    accessToken.setJsapiTicketCreateTime(new Date().getTime());
                    accessToken.setJsapiTicketExpiresIn(Integer.parseInt(jsonObject.get("expires_in").toString()));
                } else {
                    logger.error("获取授权失败");
                    return null;
                }
            } else {
                logger.error("获取授权失败,错误:" + result);
                return null;
            }
        } else {
            if (accessToken.getJsapiTicketExpiresIn() * 1000 + accessToken.getJsapiTicketCreateTime() > new Date().getTime()) {
                result = HttpUtils.getUrl(HOST + "/cgi-bin/ticket/getticket?access_token=" + accessToken.getAccessToken() + "&type=jsapi");
                jsonObject = JSON.parseObject(result);
                if (null != jsonObject.get("errcode") && Integer.parseInt(jsonObject.get("errcode").toString()) == 0) {
                    accessToken.setJsapiTicket(jsonObject.get("ticket").toString());
                    accessToken.setJsapiTicketCreateTime(new Date().getTime());
                    accessToken.setJsapiTicketExpiresIn(Integer.parseInt(jsonObject.get("expires_in").toString()));
                } else {
                    logger.error("获取授权失败");
                    return null;
                }
            }
        }
        return accessToken;
    }

}
