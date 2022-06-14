package com.quangdau.greenhouse.modelsAPI.post_writeDigital;

public class writeDigitalPost {
    private String token, cmd, houseID, port;
    private String value ;

    public writeDigitalPost(String token, String cmd, String houseID, String port, String value) {
        this.token = token;
        this.cmd = cmd;
        this.houseID = houseID;
        this.port = port;
        this.value = value;
    }
}
