package com.example.cop290.assignment2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String SharedPref = "MahPrefs";
    SharedPreferences sharedpreferences;
    Context thisContext = this;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sp = getSharedPreferences("MahPrefs", Context.MODE_PRIVATE);
        //TODO:SPLASH SCREEN ADD KARDE

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue,
                R.color.google_green,
                R.color.google_red,
                R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                on_refresh();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager mFragmentManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_notification) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.content_frame, new Notification_Fragment())
                    .commit();
        } else if (id == R.id.nav_complaint) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.content_frame, new Tab_Fragment())
                    .commit();

        } else if (id == R.id.nav_new_complaint) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction
                    .replace(R.id.content_frame, new New_Complaint_Fragment())
                    .commit();

        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void on_refresh() {
        //TODO : On refresh kya karna hai???
        Intent intent = new Intent(thisContext, LoginActivity.class);
        startActivity(intent);
    }




    public void navigate_to_complaint(View view) {
        //TODO : Navigate to the individual complaint page
        RelativeLayout rl = (RelativeLayout)view;
        TextView t = (TextView) rl.findViewById(R.id.complaint_id);
Log.i("SHREYAN", ((TextView)((RelativeLayout) view).getChildAt(1)).getText().toString() );
        Bundle bundle = new Bundle();
        bundle.putString("complaint_json",((TextView)((RelativeLayout) view).getChildAt(3)).getText().toString());

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        Complaint_Fragment f = new Complaint_Fragment();
        f.setArguments(bundle);
        xfragmentTransaction.replace(R.id.content_frame, f).addToBackStack(null).commit();
    }

    private void logout() {
        sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("kerbID", "");
        editor.putString("password", "");
        editor.putString("token", "");
        editor.commit();
        //TODO : Logout Properly!!!!!!! Done dana done done
        Intent intent = new Intent(thisContext, LoginActivity.class);
        startActivity(intent);
    }

    public void submit_new_complaint(View view) {
        // TODO : Submit new complaint
        RelativeLayout parent = (RelativeLayout)view.getParent();
        EditText title = (EditText) parent.findViewById(R.id.title);
        EditText description = (EditText) parent.findViewById(R.id.description);
        RadioButton r = (RadioButton) parent.findViewById((R.id.individual));
        Spinner spin = (Spinner) parent.findViewById(R.id.spinner);

        String selected_item = spin.getSelectedItem().toString();
        String t = title.getText().toString();
        String d = description.getText().toString();
        String isCommunity;

        if(r.isChecked()) isCommunity = "false";
        else isCommunity = "true";

        final LoadData l = new LoadData();
        l.setContext(thisContext);

        // TODO : figure out what to set for token and courseID
        l.add_complaint_request(isCommunity, selected_item, t, d, new String("courseID"));

        //add_complaint_request(String token,String isCommunity,String Type,String Title,String Description, final String courseID)
    }

    public void post_thread_comment(View view) {
        // TODO : Post a new comment on a given thread
        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        TextView thread_id = (TextView) parent.findViewById(R.id.thread_id);
        EditText description = (EditText) parent.findViewById(R.id.comment);

        String c_id = complaint_id.getText().toString();
        String t_id = thread_id.getText().toString();
        String d = description.getText().toString();

        final LoadData l = new LoadData();
        l.setContext(thisContext);

        // TODO : figure out token and posted by and time stamp
        l.new_comment_request(c_id, t_id, new String("posted by"), d, new String("time stamp"));
        //public void new_comment_request(final String token,final String complaintID,final String threadID, final String postedBy, final String description, final String timestamp)
    }

    public void post_new_thread(View view) {
        // TODO : post a new thread on a given complaint
        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        EditText title = (EditText) parent.findViewById(R.id.thread_title);
        EditText description = (EditText) parent.findViewById(R.id.thread_description);

        String id = complaint_id.getText().toString();
        String t = title.getText().toString();
        String d = description.getText().toString();

        final LoadData l = new LoadData();
        l.setContext(thisContext);

        // TODO : Figure out what to set for token
        l.new_thread_request(id, t, d);

        //public void new_thread_request(final String token,final String complaintID,final String Title, final String Description)
    }

    public void navigate_to_thread(View view) {
        // TODO : Navigate to thread
        RelativeLayout rl = (RelativeLayout)view;
        TextView t = (TextView) rl.findViewById(R.id.complaint_id);
        Log.i("SHREYAN2278194", ((TextView)((RelativeLayout) view).getChildAt(3)).getText().toString() );
        Bundle bundle = new Bundle();
        bundle.putString("thread_json",((TextView)((RelativeLayout) view).getChildAt(3)).getText().toString());

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
        Thread_Fragment f = new Thread_Fragment();
        f.setArguments(bundle);
        xfragmentTransaction.replace(R.id.content_frame, f).addToBackStack(null).commit();
    }

    public void upvote(View view) {
        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);

        String id = complaint_id.getText().toString();

        final LoadData l = new LoadData();
        l.setContext(thisContext);

        l.vote_request(id, "upvote");
    }

    public void downvote(View view) {
        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);

        String id = complaint_id.getText().toString();

        final LoadData l = new LoadData();
        l.setContext(thisContext);

        l.vote_request(id, "downvote");
    }

    public void relodge_same_authority(View view) {
       // new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Vote Error: " + error.toString()).setNeutralButton("Close", null).show();

    }

    public void relodge_higher_authority(View view) {
    }

    public void mark_resolved(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(thisContext);
        alertDialogBuilder.setTitle("/*Aayan Kumar*/");
        alertDialogBuilder
                .setMessage("Are you sure you want to mark the complaint as resolved?")
                .setCancelable(false)
                .setPositiveButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}


