package com.example.cop290.assignment2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment {


    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_, container, false);

        TextView t = (TextView) view.findViewById(R.id.name);
        final LoadData l = new LoadData();
        try{t.setText(l.loginResponseJSON.getString("name"));}catch (Exception e){e.printStackTrace();}

        return view;
    }

}
