package com.example.qrranger;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.qrranger.R;

import java.util.ArrayList;

public class SearchFragment extends Fragment{
    SearchView SearchUser;
    ListView Users;
    ArrayAdapter<String> UserAdapter;
    ArrayList<String> dataList = new ArrayList<String>();


    Activity OU_result;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        Users = (ListView) view.findViewById(R.id.SearchUserList);
        UserAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,dataList);
        Users.setAdapter(UserAdapter);
        return view;
    }
    public void onStart(){
        super.onStart();

    }


}