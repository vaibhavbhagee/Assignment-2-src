package com.example.cop290.assignment2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Thread_Fragment extends Fragment {

    private View this_fragment;

    private String complaintID;
    private String threadID;
    private String tItle;
    private String updatedon;
    private String _description;
    private JSONArray comments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thread_, container, false);

        LoadData l = new LoadData();
        l.thread_fragment = view;

        this_fragment = view;

        populate_data();
        return view;
    }

    public void populate_data() {

        View view = this_fragment;

        String threadjsonstr = getArguments().getString("thread_json");
        try {
            JSONObject threadjson = new JSONObject(threadjsonstr);
            Log.i("THREADJSON", threadjson.toString());
            threadID = threadjson.getString("thread_id");
            complaintID = threadjson.getString("complaint_id");
            tItle = threadjson.getString("title");
            updatedon = threadjson.getString("last_updated");
            _description = threadjson.getString("description");

            comments = (JSONArray)threadjson.get("comments");


        }catch(Exception e){e.printStackTrace();}

        TextView thread_id = (TextView) view.findViewById(R.id.thread_id);
        TextView complaint_id = (TextView) view.findViewById(R.id.complaint_id);
        TextView thread_title = (TextView) view.findViewById(R.id.thread_title);
        TextView thread_description = (TextView) view.findViewById(R.id.thread_description);
        TextView time = (TextView) view.findViewById(R.id.time);

        thread_id.setText(threadID);
        complaint_id.setText(complaintID);
        thread_title.setText(tItle);
        thread_description.setText(_description);
        time.setText("Last updated:" + updatedon);

        ArrayList<fraud> list = new ArrayList<fraud>();
        // TODO : add elements to the thread list
        try {
            for (int i = 0; i < comments.length(); ++i) {
                list.add(new fraud(comments.getJSONObject(i).getString("description"), comments.getJSONObject(i).getString("posted_by"), comments.getJSONObject(i).getString("timestamp")));
            }
        }catch(Exception e){e.printStackTrace();}
        UserAdapter adapter = new UserAdapter(getActivity(), list);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);
    }
    public class UserAdapter extends ArrayAdapter<fraud> {
        public UserAdapter(Context context, ArrayList<fraud> items) {
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            fraud item = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.format_comment, parent, false);
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
