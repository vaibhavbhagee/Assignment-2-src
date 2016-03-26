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
public class Complaint_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complaint_, container, false);
        populate_data(view);
        return view;
    }

    private void populate_data(View view) {

        TextView complaint_type = (TextView) view.findViewById(R.id.type);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView lodged_on = (TextView) view.findViewById(R.id.lodged_on);
        TextView updated_on = (TextView) view.findViewById(R.id.updated_on);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView current_authority = (TextView) view.findViewById(R.id.current_authority);

        complaint_type.setText("");
        title.setText("");
        lodged_on.setText("");
        updated_on.setText("");
        description.setText("");
        current_authority.setText("");


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

            TextView slno = (TextView) convertView.findViewById(R.id.slno);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView lodger = (TextView) convertView.findViewById(R.id.lodger);
            RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            TextView thread_id = (TextView) convertView.findViewById(R.id.thread_id);

            slno.setText((position+1)+"");
            title.setText(item.title);
            lodger.setText(item.lodger);
            layout.setBackgroundColor(getResources().getColor(R.color.resolved));
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
