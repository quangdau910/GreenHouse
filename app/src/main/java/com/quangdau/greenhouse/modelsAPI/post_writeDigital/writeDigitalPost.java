package com.quangdau.greenhouse.modelsAPI.post_writeDigital;

public class writeDigitalPost {
    private String token, request, houseID, port;
    private String value ;

    public writeDigitalPost(String token, String request, String houseID, String port, String value) {
        this.token = token;
        this.request = request;
        this.houseID = houseID;
        this.port = port;
        this.value = value;
    }
}
