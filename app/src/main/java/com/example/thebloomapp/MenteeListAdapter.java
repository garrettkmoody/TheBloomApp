package com.example.thebloomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MenteeListAdapter extends ArrayAdapter<Mentee> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    int mResource;
    public MenteeListAdapter(Context context, int resource, ArrayList<Mentee> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String year = getItem(position).getYear();
        String birthday = getItem(position).getBirthday();

        Mentee mentee = new Mentee(name,year,birthday);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvYear = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvYear.setText(year);
        tvBirthday.setText(birthday);

        return convertView;

    }
}
