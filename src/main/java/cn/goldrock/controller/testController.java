package cn.goldrock.controller;

import cn.goldrock.entity.AccessToken;
import cn.goldrock.entity.WxConfig;
import cn.goldrock.service.AccessService;
import cn.goldrock.utils.HttpUtils;
import cn.goldrock.utils.SignatureUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

/**
 * Created by bluers on 2018/3/29.
 */
@Controller
public class testController {

    private static Logger logger = LoggerFactory.getLogger(testController.class);

    @Value("${wx.AppID}")
    private String AppID;

    @Value("${wx.AppSecret}")
    private String AppSecret;

    @Value("${wx.nonceStr}")
    private String nonceStr;

    @Autowired
    AccessService accessService;


    @RequestMapping(value="/weixin", method=RequestMethod.GET)
    public String getWxConfig(Map<String, Object> model){

        AccessToken accessToken = accessService.getAccessToken(AppID, AppSecret,nonceStr);

        if(null == accessToken){
            logger.error("获取授权失败");
        }

        WxConfig wxConfig = new WxConfig();

        wxConfig.setTicket(accessToken.getJsapiTicket());
        wxConfig.setAppId(accessToken.getAppId());
        wxConfig.setTimestamp(new Date().getTime()/1000);
        wxConfig.setNonceStr(accessToken.getNonceStr());
        wxConfig.setUrl("http://wx.goldrock.cn/weixin");
        wxConfig.setSignature(generateSignature(wxConfig));
        model.put("wxConfig", JSON.toJSONString(wxConfig));
        model.put("title", "你不知道的生物圈");
        logger.info("[获取授权]" + wxConfig);
        return "index";
    }

    private String generateSignature(WxConfig wxConfig){
        String result = "";
        try{
            result = SignatureUtils.SHA1("jsapi_ticket=" + wxConfig.getTicket() + "&noncestr=" + wxConfig.getNonceStr() + "&timestamp=" + wxConfig.getTimestamp() + "&url=" + wxConfig.getUrl());
        }catch(Exception e){
            logger.error("[签名失败]" + wxConfig);
        }
        return result;
    }


}
