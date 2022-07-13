package com.quangdau.greenhouse.modelsAPI.get_userInformation;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ObjUserInformation {
    private String account, userName, email, country, dateOfBirth;
    private ArrayList<String> authority;


    public String getDateOfBirth() {
        return formatDate(dateOfBirth);
    }

    public String getAccount() {
        return account;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getCountry() {
        return country;
    }

    public ArrayList<String> getAuthority() {
        return authority;
    }


    private String formatDate(String date){
        OffsetDateTime dateTimeWithOffset = Instant.parse(date).atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toOffsetDateTime();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fmt.format(dateTimeWithOffset);
    }
}
