package com.example.cop290.assignment2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Complaint_unresolved extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_unresolved, container, false);
        populateListView(view);
        return view;
    }

    void populateListView(View view) {

        ArrayList<fraud> list = new ArrayList<fraud>();

        // TODO : add elements to the list
        // for(int i=0; i<something.length; ++i ){
        //     list.add(new fraud(bla, bla, bla));
        // }

        UserAdapter adapter = new UserAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public class UserAdapter extends ArrayAdapter<fraud> {
        public UserAdapter(Context context, ArrayList<fraud> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            fraud item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_complaint, parent, false);
            }
            // TODO : populate the elements of the list view here

            // TextView slno = (TextView) convertView.findViewById(R.id.slno);
            // slno.setText((position+1)+"");

            return convertView;
        }
    }
    public class fraud
    {
        public String notification;
        public String time;

        public fraud(String notification,String time) {
            this.notification = notification;
            this.time = time;
        }
    }
}
