package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentContainer {

    @SerializedName("studentaccounts")
    private List<StudentAccount> myStudentAccounts;

    public List<StudentAccount> getMyStudentAccounts() {
        return myStudentAccounts;
    }
}
