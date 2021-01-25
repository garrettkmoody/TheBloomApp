package com.moody.thebloomapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MenteeListAdapter extends ArrayAdapter<Mentee> {

    private static final String TAG = "PersonListAdapter";

    private final Context mContext;
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

        TextView tvName = convertView.findViewById(R.id.textView1);
        TextView tvYear = convertView.findViewById(R.id.textView2);
        TextView tvBirthday = convertView.findViewById(R.id.goaloraddtv);

        tvName.setText(name);
        tvYear.setText(year);
        tvBirthday.setText(birthday);

        return convertView;

    }
}
