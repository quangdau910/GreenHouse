package com.quangdau.greenhouse.modelsAPI.get_history;

public class ObjHistoryLoginData {
    private String account, ip, device_name, login_time;
    private boolean expanded;

    public String getAccount() {
        return account;
    }

    public String getIp() {
        return ip;
    }

    public String getDevice_name() {
        return device_name;
    }

    public String getLogin_time() {
        return login_time;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


}
