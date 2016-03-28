package com.example.cop290.assignment2;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Complaint_Fragment extends Fragment {

    ArrayList<fraud> list = new ArrayList<fraud>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_, container, false);
        populate_data(view);
        return view;
    }

    private void populate_data(View view) {

        TextView complaint_id = (TextView) view.findViewById(R.id.complaint_id);
        TextView complaint_type = (TextView) view.findViewById(R.id.type);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView lodged_on = (TextView) view.findViewById(R.id.lodged_on);
        TextView updated_on = (TextView) view.findViewById(R.id.updated_on);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView current_authority = (TextView) view.findViewById(R.id.current_authority);
        Button button1 = (Button) view.findViewById(R.id.button1);
        Button button2 = (Button) view.findViewById(R.id.button2);
        Button button3 = (Button) view.findViewById(R.id.button3);
        FloatingActionButton upvote = (FloatingActionButton) view.findViewById(R.id.upvote);
        FloatingActionButton downvote = (FloatingActionButton) view.findViewById(R.id.downvote);
        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);

        // TODO : Set these values
        complaint_id.setText("Complaint ID");
        complaint_type.setText("Community");
        title.setText("Title of the complaint");
        lodged_on.setText("14th December 2016");
        updated_on.setText("15th December 2016");
        description.setText("Bla Bla Bla Bla. Shreyan kya kar raha hai, kya chahata hai?");
        current_authority.setText("Current Authority");
        String current_status = "unresolved";

        if(current_status.equals("resolved")) layout.setBackgroundColor(getResources().getColor(R.color.resolved));
        else if(current_status.equals("under_resolution")) layout.setBackgroundColor(getResources().getColor(R.color.under_resolution));
        else layout.setBackgroundColor(getResources().getColor(R.color.unresolved));

        if(complaint_type.equals("individial")){
            upvote.setVisibility(View.GONE);
            downvote.setVisibility(View.GONE);
        }

        // TODO : IF THEN ELSE KE CHECKS LIKHNA HAI!!!
        if(current_status.equals("unresolved") && (true) /* authority hai? */  ){
            if(true /* highest authority hai */){
                button1.setVisibility(View.GONE);
                button2.setVisibility(View.GONE);
            }else{
                button1.setVisibility(View.GONE);
            }
        }else if(complaint_type.equals("under_resolution") && (true) /* user hai? */){

        }else{
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
        }

        list = new ArrayList<fraud>();
        // TODO ing : add elements to the thread list

        //LoadData l = new LoadData();

        for(int i=0; i<10; ++i ){
            list.add(new fraud("Title "+i, "Lodger "+i, "bla"));
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

            TextView slno = (TextView) convertView.findViewById(R.id.slno);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView lodger = (TextView) convertView.findViewById(R.id.lodger);
            //RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            TextView thread_id = (TextView) convertView.findViewById(R.id.thread_id);

            slno.setText((position+1)+"");
            title.setText(item.title);
            lodger.setText(item.lodger);
            //layout.setBackgroundColor(getResources().getColor(R.color.resolved));
            thread_id.setText(item.thread_id);

            return convertView;
        }
    }
    public class fraud
    {
        public String title;
        public String lodger;
        public String thread_id;

        public fraud(String title,String lodger, String thread_id) {
            this.title = title;
            this.lodger = lodger;
            this.thread_id = thread_id;
        }
    }
}
