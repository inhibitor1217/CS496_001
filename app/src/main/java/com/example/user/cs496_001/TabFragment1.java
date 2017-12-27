package com.example.user.cs496_001;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class TabFragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        ListView listView = (ListView) view.findViewById(R.id.list);

        CustomAdapter customAdapter = new CustomAdapter(getActivity(), R.layout.custom_listview, MainActivity.CONTACTS);
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pair<String, String> item = (Pair<String, String>) adapterView.getAdapter().getItem(i);

                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("연락처");
                alert.setMessage("이름 : " + item.first + "\n전화번호 : " + item.second);
                alert.setPositiveButton("확인", null);
                alert.show();

            }
        });

        return view;

    }

}
