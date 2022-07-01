package com.quangdau.greenhouse.modelsAPI.res_renewTokenPost;

public class resRenewTokenPost {
    private String token, response, account;
    private long expTime;

    public long getExpTime() {
        return expTime;
    }

    public String getToken() {
        return token;
    }

    public String getResponse() {
        return response;
    }

    public String getAccount() {
        return account;
    }
}
