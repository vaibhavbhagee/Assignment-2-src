package com.example.cop290.assignment2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class New_Complaint_Fragment extends Fragment implements AdapterView.OnItemSelectedListener
{
    private View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_complaint_, container, false);
        ((Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle("New Complaint");
        populate_spinner(view);
        v = view;
        return view;
    }

    public void populate_spinner(View view){
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);

        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        LoadData l = new LoadData();
        List<String> courses = new ArrayList<String>();

        try {
        if(l.loginResponseJSON.getJSONArray("tags").length() >0 ) {
            categories.add("Mess Complaint");
            categories.add("Maintenance Complaint");
            categories.add("NCC/NSO/NSS Complaint");
        }
        if(l.loginResponseJSON.getJSONArray("course_list").length() >0)
        categories.add("Course Complaint");

        categories.add("Student Welfare Complaint");
        categories.add("Infrastructure Complaint");
        categories.add("Security Complaint");



            JSONArray ja = l.loginResponseJSON.getJSONArray("course_list");
            for(int i = 0; i < ja.length() ; i ++)
                courses.add(ja.getString(i).toUpperCase());
        }catch(Exception e){e.printStackTrace();}

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        Spinner spinner2 = (Spinner) v.findViewById(R.id.spinner2);

        if(item.equals("Course Complaint")){
            spinner2.setVisibility(View.VISIBLE);
        }else{
            spinner2.setVisibility(View.GONE);
        }
        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}
