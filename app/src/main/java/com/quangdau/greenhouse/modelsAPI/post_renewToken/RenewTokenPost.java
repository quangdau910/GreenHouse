package com.quangdau.greenhouse.modelsAPI.post_renewToken;

public class RenewTokenPost {
    private String token, request;

    public RenewTokenPost(String token, String request) {
        this.token = token;
        this.request = request;
    }
}
