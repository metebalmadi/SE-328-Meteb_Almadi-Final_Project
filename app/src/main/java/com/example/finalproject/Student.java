package com.example.finalproject;

public class Student {
    private String stID;
    private String stName;
    private String stFather;
    private String stSurname;
    private String stNationalID;
    private String stDOB;
    private String stGender;

    public Student() {
    }

    public Student(String stID, String stName, String stFather, String stSurname, String stNationalID, String stDOB, String stGender) {
        this.stID = stID;
        this.stName = stName;
        this.stFather = stFather;
        this.stSurname = stSurname;
        this.stNationalID = stNationalID;
        this.stDOB = stDOB;
        this.stGender = stGender;
    }

    public String getStID() {
        return stID;
    }

    public void setStID(String stID) {
        this.stID = stID;
    }

    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStFather() {
        return stFather;
    }

    public void setStFather(String stFather) {
        this.stFather = stFather;
    }

    public String getStSurname() {
        return stSurname;
    }

    public void setStSurname(String stSurname) {
        this.stSurname = stSurname;
    }

    public String getStNationalID() {
        return stNationalID;
    }

    public void setStNationalID(String stNationalID) {
        this.stNationalID = stNationalID;
    }

    public String getStDOB() {
        return stDOB;
    }

    public void setStDOB(String stDOB) {
        this.stDOB = stDOB;
    }

    public String getStGender() {
        return stGender;
    }

    public void setStGender(String stGender) {
        this.stGender = stGender;
    }
}
