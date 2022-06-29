package com.quangdau.greenhouse.modelsAPI.post_logout;

public class LogoutPost {
    private String token;
    final private String request = "Logout";

    public LogoutPost(String token) {
        this.token = token;
    }
}
