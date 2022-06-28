package com.quangdau.greenhouse.modelsAPI.res_authenPost;

import java.util.ArrayList;

public class resAuthorityPost {
    private String account, token, response;
    private long expTime;
    private ArrayList<String> authority;

    public String getAccount() {
        return account;
    }

    public String getToken() {
        return token;
    }

    public String getResponse() {
        return response;
    }
    public long getExpTime() {
        return expTime;
    }

    public ArrayList<String> getAuthority() {
        return authority;
    }
}
