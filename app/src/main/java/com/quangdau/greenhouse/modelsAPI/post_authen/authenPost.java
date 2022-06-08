package com.quangdau.greenhouse.modelsAPI.post_authen;

public class authenPost {
    private String account, password, cmd, device_name, ip;

    public authenPost(String cmd, String account, String password, String device_name, String ip) {
        this.account = account;
        this.password = password;
        this.cmd = cmd;
        this.device_name = device_name;
        this.ip = ip;
    }
}
