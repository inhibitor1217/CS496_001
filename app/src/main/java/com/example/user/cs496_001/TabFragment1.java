package com.example.user.cs496_001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by user on 2017-12-26.
 */


public class TabFragment1 extends Fragment {

    private ListView listView;
    private Pair<ArrayList<String>, ArrayList<String>> contact;

    public void setContact(Pair<ArrayList<String>, ArrayList<String>> contact) {
        this.contact = contact;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.tab_fragment_1, container, false);
    }
}
