package com.example.thebloomapp;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Singleton  {

    private static Singleton mInstance;
    private ArrayList<Mentee> list = null;

    public static Singleton getInstance() {
        if(mInstance == null)
            mInstance = new Singleton();

        return mInstance;
    }

    private Singleton() {
        list = new ArrayList<Mentee>();
    }
    // retrieve array from anywhere
    public ArrayList<Mentee> getArray() {
        return this.list;
    }
    //Add element to array
    public void addToArray(Mentee value) {
        list.add(value);
    }


    }


