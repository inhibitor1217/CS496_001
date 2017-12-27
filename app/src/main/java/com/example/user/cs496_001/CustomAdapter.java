package com.example.user.cs496_001;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Pair<String, String>> {

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<Pair<String, String>> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        TextView text_name, text_number;
        ImageView img_person;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview, viewGroup, false);
        }

        text_name = (TextView) convertView.findViewById(R.id.name);
        text_number = (TextView) convertView.findViewById(R.id.number);
        img_person = (ImageView) convertView.findViewById(R.id.img);

        Pair<String, String> obj = getItem(i);
        text_name.setText(obj.first);
        text_number.setText(obj.second);

        return convertView;

    }
}
