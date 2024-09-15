package com.example.shahzadasadfyp.Model;

public class User {
    private String userId; // Add this field
    private String name;
    private String email;
    private String userType;
    private String profileImage; // URL of the profile image
    private String dob; // Date of Birth
    private String age;
    private String gender;
    private String address;
    private String phone;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
        // Default constructor
    }

    // Parameterized constructor
    public User(String name,String userId, String email, String userType, String profileImage, String dob, String age, String gender, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.userType = userType;
        this.profileImage = profileImage;
        this.dob = dob;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
