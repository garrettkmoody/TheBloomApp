package com.moody.thebloomapp;

public class Mentee {
    private String name;
    private String year;
    private String birthday;

    public Mentee(String name, String year, String birthday) {
        this.name = name;
        this.year = year;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


}
