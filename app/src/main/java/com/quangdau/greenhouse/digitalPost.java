package com.quangdau.greenhouse;

public class digitalPost {
    private String token, cmd, houseID, port;
    private Character value ;

    public digitalPost(String token, String cmd, String houseID, String port, Character value) {
        this.token = token;
        this.cmd = cmd;
        this.houseID = houseID;
        this.port = port;
        this.value = value;
    }
}
