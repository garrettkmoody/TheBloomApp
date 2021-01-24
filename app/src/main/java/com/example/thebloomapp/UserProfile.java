package com.example.thebloomapp;


import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UserProfile {
    private String name;
    private String email;
    private String age;
    private String link;
    private String uid;
    private String dob;
    private String establishment;
    private String service;
    private String notes;
    private String coach;
    private boolean isacoach;
    private List<String> goals;
    private List<String> scores;

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

    public void setEstablishment(String establishment) { this.establishment = establishment; }

    public void setService(String service) { this.service = service; }

    public void setGoals(List<String> goals) { this.goals = goals; }

    public void setScores(List<String> scores) { this.scores = scores; }

    public void setNotes(String notes) { this.notes = notes; }

    public void setCoach(String coach) { this.coach = coach; }

    public void setIsacoach(boolean isacoach) { this.isacoach = isacoach; }

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

    public String getEstablishment() { return establishment; }

    public String getService() { return service; }

    public List<String> getGoals() { return goals; }

    public List<String> getScores() { return scores; }

    public String getNotes() { return notes; }

    public String getCoach() { return coach; }

    public boolean getIsacoach() { return isacoach; }

    public UserProfile(String Name, String Email, String Age, String Link) {
        this.name = Name;
        this.email = Email;
        this.age = Age;
        this.link = Link;
    }
}
