package com.quangdau.greenhouse.modelsAPI.post_removeToken;

public class RemoveTokenPost {
    private String token;
    final private String request = "RemoveToken";

    public RemoveTokenPost(String token) {
        this.token = token;
    }
}
