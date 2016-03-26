package com.example.cop290.assignment2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Thread_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_, container, false);
        populate_data(view);
        return view;
    }

    private void populate_data(View view) {

        TextView thread_title = (TextView) view.findViewById(R.id.thread_title);
        TextView thread_description = (TextView) view.findViewById(R.id.thread_description);
        TextView time = (TextView) view.findViewById(R.id.time);

        thread_title.setText("");
        thread_description.setText("");
        time.setText("");

        ArrayList<fraud> list = new ArrayList<fraud>();
        // TODO : add elements to the thread list
        for(int i=0; i<10; ++i ){
            //list.add(new fraud("Title ka naam kya hona chaiyeh?? Ion madarboard hai.\n New line karke kya milega tujhe? "+i, "Lodger "+i, "bla"));
        }
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_thread, parent, false);
            }
            // TODO : populate the elements of the list view here

            TextView description = (TextView) convertView.findViewById(R.id.description);
            TextView posted_by = (TextView) convertView.findViewById(R.id.posted_by);
            TextView posted_on = (TextView) convertView.findViewById(R.id.posted_on);

            description.setText(item.description);
            posted_by.setText(item.posted_by);
            posted_on.setText(item.posted_on);

            return convertView;
        }
    }
    public class fraud
    {
        public String description;
        public String posted_by;
        public String posted_on;

        public fraud(String description,String posted_by, String posted_on) {
            this.description = description;
            this.posted_by = posted_by;
            this.posted_on = posted_on;
        }
    }

}
