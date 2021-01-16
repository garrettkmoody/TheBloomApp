package com.example.thebloomapp;


public class UserProfile {
    private String name;
    private String email;
    private String age;
    private String link;

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

    public UserProfile(String Name, String Email, String Age, String Link) {
        this.name = Name;
        this.email = Email;
        this.age = Age;
        this.link = Link;
    }
}
