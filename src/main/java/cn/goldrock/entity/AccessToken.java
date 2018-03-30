package cn.goldrock.entity;

import java.util.Date;

/**
 * Created by bluers on 2018/3/29.
 */
public class AccessToken {
    private String appId;
    private String appSecret;
    private String nonceStr;
    private String accessToken;
    private int accessTokenExpiresIn;
    private long accessTokenCreateTime;
    private String jsapiTicket;
    private int jsapiTicketExpiresIn;
    private long jsapiTicketCreateTime;

    public AccessToken() {
    }

    public AccessToken(String appId, String appSecret, String nonceStr) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.nonceStr = nonceStr;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", nonceStr='" + nonceStr + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessTokenExpiresIn=" + accessTokenExpiresIn +
                ", accessTokenCreateTime=" + accessTokenCreateTime +
                ", jsapiTicket='" + jsapiTicket + '\'' +
                ", jsapiTicketExpiresIn=" + jsapiTicketExpiresIn +
                ", jsapiTicketCreateTime=" + jsapiTicketCreateTime +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getAccessTokenExpiresIn() {
        return accessTokenExpiresIn;
    }

    public void setAccessTokenExpiresIn(int accessTokenExpiresIn) {
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

    public long getAccessTokenCreateTime() {
        return accessTokenCreateTime;
    }

    public void setAccessTokenCreateTime(long accessTokenCreateTime) {
        this.accessTokenCreateTime = accessTokenCreateTime;
    }

    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket;
    }

    public int getJsapiTicketExpiresIn() {
        return jsapiTicketExpiresIn;
    }

    public void setJsapiTicketExpiresIn(int jsapiTicketExpiresIn) {
        this.jsapiTicketExpiresIn = jsapiTicketExpiresIn;
    }

    public long getJsapiTicketCreateTime() {
        return jsapiTicketCreateTime;
    }

    public void setJsapiTicketCreateTime(long jsapiTicketCreateTime) {
        this.jsapiTicketCreateTime = jsapiTicketCreateTime;
    }
}
