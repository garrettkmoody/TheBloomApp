package com.example.thebloomapp;


import java.util.Vector;

public class UserProfile {
    private String name;
    private String email;
    private String age;
    private String link;
    private String uid;
    private String dob;


    public UserProfile() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setLink(String link) { this.link = link; }

    public void setUid(String uid) { this.uid = uid; }

    public void setDob(String dob) { this.dob = dob; }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getLink() { return link; }

    public String getUid() { return uid; }

    public String getDob() { return dob; }

    public UserProfile(String Name, String Email, String Age, String Link) {
        this.name = Name;
        this.email = Email;
        this.age = Age;
        this.link = Link;
    }
}
