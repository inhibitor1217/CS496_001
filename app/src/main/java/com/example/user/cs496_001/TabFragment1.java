package com.example.user.cs496_001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TabFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list);

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), R.layout.custom_listview, MainActivity.CONTACTS);
        listView.setAdapter(customAdapter);

        return view;

    }

}
