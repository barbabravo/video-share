package cn.goldrock.entity;

/**
 * Created by bluers on 2018/3/29.
 */
public class WxConfig {
    private String ticket;
    private String appId;
    private long timestamp;
    private String nonceStr;
    private String signature;
    private String url;

    @Override
    public String toString() {
        return "WxConfig{" +
                "ticket='" + ticket + '\'' +
                ", appId='" + appId + '\'' +
                ", timestamp=" + timestamp +
                ", nonceStr='" + nonceStr + '\'' +
                ", signature='" + signature + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
