package com.example.qrranger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchFragment extends Fragment {
    SearchView SearchUser;
    ListView Users;
    ArrayAdapter<String> UserAdapter;
    // just test delete when database connected
    String[] random = {"a", "b"};
    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Users = (ListView) view.findViewById(R.id.search_users_list);
        UserAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,random);
        Users.setAdapter(UserAdapter);
        return view;

    }

}