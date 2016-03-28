package com.example.cop290.assignment2;
//TODO: SHOW ONLY UNSEEN NOTIFS

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notification_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification_, container, false);
        ((Toolbar)getActivity().findViewById(R.id.toolbar)).setTitle("Notifications");


        LoadData l = new LoadData();

        populateListView(view);
        return view;
    }

    void populateListView(View view) {

        ArrayList<fraud> list = new ArrayList<fraud>();

        // TODO : add elements to the list

        LoadData l = new LoadData();
        JSONObject[] j = l.notificationsArray;

        try {
            for (int i = 0; i < j.length; ++i) {

                list.add(new fraud(j[i].getString("content"), j[i].getString("timestamp"),lookfor(j[i].getString("complaint_id")).toString() ));
            }
        }catch(Exception e){e.printStackTrace();}
        if(list.size()==0){
            TextView t = (TextView) view.findViewById(R.id.no_notification);
            t.setVisibility(View.VISIBLE);
        }

        UserAdapter adapter = new UserAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    public JSONObject lookfor(String compl_id)
    {
        LoadData l = new LoadData();
        JSONObject[] j = l.complaintDetailsArray;
        try {
            for (int i = 0; i < j.length; i++) {
                if (j[i].getString("complaint_id").equals(compl_id))
                    return j[i];
            }
        }catch(Exception e){e.printStackTrace();}
        return null;
    }

    public class UserAdapter extends ArrayAdapter<fraud> {
        public UserAdapter(Context context, ArrayList<fraud> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            fraud item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_notification, parent, false);
            }
            // TODO : populate the elements of the list view here

            TextView slno = (TextView) convertView.findViewById(R.id.slno);
            TextView desc = (TextView) convertView.findViewById(R.id.description);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView complaint_id = (TextView) convertView.findViewById(R.id.complaint_id);

            slno.setText((position+1)+"");
            desc.setText(item.desc);
            time.setText(item.time);
            complaint_id.setText(item.complaint_id);

            return convertView;
        }
    }
    public class fraud
    {
        public String desc;
        public String time;
        public String complaint_id;

        public fraud(String desc,String time, String complaint_id) {
            this.desc = desc;
            this.time = time;
            this.complaint_id = complaint_id;
        }
    }
}
