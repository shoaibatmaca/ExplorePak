package com.example.shahzadasadfyp.Model;



public class Complaint {
    private int profileImageResId;
    private String userName;

    public Complaint(int profileImageResId, String userName) {
        this.profileImageResId = profileImageResId;
        this.userName = userName;
    }

    public int getProfileImageResId() {
        return profileImageResId;
    }

    public String getUserName() {
        return userName;
    }
}
