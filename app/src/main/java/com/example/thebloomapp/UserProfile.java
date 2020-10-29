package com.example.thebloomapp;

public class UserProfile {
    public String Name;
    public String Email;
    public String Age;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public UserProfile(String name, String email, String age) {
        Name = name;
        Email = email;
        Age = age;
    }
}
