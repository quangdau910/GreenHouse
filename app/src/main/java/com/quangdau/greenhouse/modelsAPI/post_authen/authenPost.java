package com.quangdau.greenhouse.modelsAPI.post_authen;

public class authenPost {
    private String account, password, request, device_name, ip;

    public authenPost(String request, String account, String password, String device_name, String ip) {
        this.account = account;
        this.password = password;
        this.request = request;
        this.device_name = device_name;
        this.ip = ip;
    }
}
