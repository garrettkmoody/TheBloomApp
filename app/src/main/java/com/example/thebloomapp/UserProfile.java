package com.example.thebloomapp;

public class UserProfile {
    private String name;
    private String email;
    private String age;

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


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public UserProfile(String Name, String Email, String Age) {
        this.name = Name;
        this.email = Email;
        this.age = Age;
    }
}
