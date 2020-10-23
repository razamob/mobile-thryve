package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

public class StudentAuth {

    private int id;

    @SerializedName("sheridan_id")
    private String sheridanId;

    private String password;

    public StudentAuth(String sheridanId, String password) {
        this.sheridanId = sheridanId;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getSheridanId() {
        return sheridanId;
    }

    public String getPassword() {
        return password;
    }
}
