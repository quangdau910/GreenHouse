package com.quangdau.greenhouse.modelsAPI.res_authenPost;

import java.util.ArrayList;

public class resAuthorityPost {
    private String account, cmd, token, response;
    private ArrayList<String> authority;

    public String getAccount() {
        return account;
    }

    public String getCmd() {
        return cmd;
    }

    public String getToken() {
        return token;
    }

    public String getResponse() {
        return response;
    }

    public ArrayList<String> getAuthority() {
        return authority;
    }
}
