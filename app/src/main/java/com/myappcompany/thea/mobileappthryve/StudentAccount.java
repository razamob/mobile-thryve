package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentAccount implements Serializable {
    private int id;

    @SerializedName("fname")
    private String firstName;

    @SerializedName("lname")
    private String lastName;

    @SerializedName("email")
    private String emailAddress;

    @SerializedName("student_number")
    private String studentNumber;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("program_year")
    private int programYear;

    private boolean als;

    private boolean coop;

    private boolean international;

    @SerializedName("program_id_id")
    private int programId;

    @SerializedName("auth_id_id")
    private int authId;

    public StudentAccount(String firstName, String lastName, String emailAddress, String studentNumber, String phoneNumber, int programYear, boolean als, boolean coop, boolean international, int programId, int authId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.studentNumber = studentNumber;
        this.phoneNumber = phoneNumber;
        this.programYear = programYear;
        this.als = als;
        this.coop = coop;
        this.international = international;
        this.programId = programId;
        this.authId = authId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getProgramYear() {
        return programYear;
    }

    public boolean isAls() {
        return als;
    }

    public boolean isCoop() {
        return coop;
    }

    public boolean isInternational() {
        return international;
    }

    public int getProgramId() {
        return programId;
    }

    public int getAuthId() {
        return authId;
    }

    public void setId(int id) {
        this.id = id;
    }
}
