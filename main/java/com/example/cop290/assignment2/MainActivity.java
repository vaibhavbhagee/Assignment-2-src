package com.example.cop290.assignment2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

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



    public void navigate_to_complaint(View view) {
        //TODO : Navigate to the individual complaint page
        RelativeLayout rl = (RelativeLayout)view;
        TextView t = (TextView) rl.findViewById(R.id.complaint_id);

        Log.i("SHREYAN", ((TextView) ((RelativeLayout) view).getChildAt(3)).getText().toString());

        Bundle bundle = new Bundle();
        bundle.putString("complaint_json", ((TextView) ((RelativeLayout) view).getChildAt(3)).getText().toString());

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
        Intent intent = new Intent(thisContext, LoginActivity.class);
        startActivity(intent);
        //TODO  : Seriously, koi dhang se kar le yeh??
        //TODO ?:Aur kya karna hai bc
    }

    public void submit_new_complaint(View view) {
        // TODO : Submit new complaint
        RelativeLayout parent = (RelativeLayout) view.getParent();
        EditText title = (EditText) parent.findViewById(R.id.title);
        EditText description = (EditText) parent.findViewById(R.id.description);
        RadioButton r = (RadioButton) parent.findViewById((R.id.individual));
        Spinner spin = (Spinner) parent.findViewById(R.id.spinner);

        String selected_item = spin.getSelectedItem().toString();
        String t = title.getText().toString();
        String d = description.getText().toString();
        String isCommunity;

        if (t.equals("")) {
            Toast.makeText(MainActivity.this, "Title is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        if (d.equals("")) {
            Toast.makeText(MainActivity.this, "Description is Empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (r.isChecked()) isCommunity = "false";
        else isCommunity = "true";

        final LoadData l = new LoadData();
        l.setContext(thisContext);
        l.add_complaint_request(isCommunity, selected_item, t, d, new String("courseID"));

        timer2(0, l);
        l.flag[2] = false;

        // TODO : figure out what to set for token and courseID
        // TODO : Arre yaar, abhi tak nahi hua hai yeh!

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

        if(d.equals("")){
            Toast.makeText(MainActivity.this, "Comment is Empty", Toast.LENGTH_LONG).show();
            return;
        }

        final LoadData l = new LoadData();
        l.setContext(thisContext);
        l.new_comment_request(c_id, t_id, new String("posted by"), d, new String("time stamp"));
        timer4(0, l);
        l.flag[4] = false;

        // TODO : figure out token and posted by and time stamp

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

        if(t.equals("")){
            Toast.makeText(MainActivity.this, "Title is Empty", Toast.LENGTH_LONG).show();
            return;
        }
        if(d.equals("")){
            Toast.makeText(MainActivity.this, "Description is Empty", Toast.LENGTH_LONG).show();
            return;
        }

        final LoadData l = new LoadData();
        l.setContext(thisContext);
        l.new_thread_request(id, t, d);
        timer3(0, l);
        l.flag[3] = false;

        //public void new_thread_request(final String token,final String complaintID,final String Title, final String Description)
    }

    public void navigate_to_thread(View view) {
        // TODO : Navigate to thread
        RelativeLayout rl = (RelativeLayout)view;
        TextView t = (TextView) rl.findViewById(R.id.complaint_id);
        Log.i("SHREYAN2278194", ((TextView)((RelativeLayout) view).getChildAt(3)).getText().toString() );
        Bundle bundle = new Bundle();
        bundle.putString("thread_json", ((TextView) ((RelativeLayout) view).getChildAt(3)).getText().toString());

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

        // TODO : Check if upvote is possible
        if(false /*Upvote not possible*/ ){
            Toast.makeText(MainActivity.this, "Sorry, you have already voted", Toast.LENGTH_LONG).show();
            return;
        }

        final LoadData l = new LoadData();
        l.setContext(thisContext);
        l.vote_request(id, "upvote");
        timer8(0, l);
        l.flag[8] = false;
    }

    public void downvote(View view) {

        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        String id = complaint_id.getText().toString();

        // TODO : Check if downvote is possible
        if(false /*Downvote not possible*/ ){
            Toast.makeText(MainActivity.this, "Sorry, you have already voted", Toast.LENGTH_LONG).show();
            return;
        }

        final LoadData l = new LoadData();
        l.setContext(thisContext);
        l.vote_request(id, "downvote");
        timer8(0, l);
        l.flag[8] = false;
    }

    public void relodge_same_authority(View view) {

        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        String id = complaint_id.getText().toString();

        String title = "Title kya rakhun?";
        String message = "Are you sure you want to relodge the complaint with same authority?";
        alert(id, title, message, 1);
    }

    public void relodge_higher_authority(View view) {

        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        String id = complaint_id.getText().toString();

        String title = "Title kya rakhun?";
        String message = "Are you sure you want to relodge the complaint with higher authority?";
        alert(id, title, message, 2);
    }

    public void mark_resolved(View view) {

        RelativeLayout parent = (RelativeLayout)view.getParent();
        TextView complaint_id = (TextView) parent.findViewById(R.id.complaint_id);
        String id = complaint_id.getText().toString();

        String title = "Title kya rakhun?";
        String message = "Are you sure you want to mark the complaint as resolved?";
        alert(id, title, message, 3);
    }

    private void alert(final String complaint_id, String title, String message, final int type){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(thisContext);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        final LoadData l = new LoadData();
                        l.setContext(thisContext);

                        if(type == 1){          //same authority
                            l.relodge_same_request(complaint_id);
                            timer7(0,l);
                            l.flag[7] = false;
                        }else if(type == 2){    //higher authority
                            l.relodge_higher_request(complaint_id);
                            timer6(0,l);
                            l.flag[6] = false;
                        }else if(type == 3){    //mark resolved
                            l.mark_resolved_request(complaint_id);
                            timer5(0,l);
                            l.flag[5] = false;
                        }
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

    public boolean timer2(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[2]){
                    Toast.makeText(MainActivity.this,"New complaint posted", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer2(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer4(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[4]){
                    Toast.makeText(MainActivity.this,"New comment posted", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer4(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer3(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[3]){
                    Toast.makeText(MainActivity.this,"New thread posted", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer3(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer8(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[8]){
                    try {
                        if (l.voteResponse.getString("success").equals("true"))
                            Toast.makeText(MainActivity.this, "Vote Recorded", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Already Voted. One account, one vote.", Toast.LENGTH_LONG).show();
                    }catch(Exception e){e.printStackTrace();}
                    on_refresh();
                } else {
                    timer8(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer5(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[5]){
                    Toast.makeText(MainActivity.this,"Complaint Marked as Resolved", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer5(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer6(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[6]){
                    Toast.makeText(MainActivity.this,"Complaint Posted with higher Authority", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer6(x + 1, l);
                }
            }
        }.start();
        return true;
    }
    public boolean timer7(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(MainActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[7]){
                    Toast.makeText(MainActivity.this,"Complaint Posted with same Authority", Toast.LENGTH_LONG).show();
                    on_refresh();
                } else {
                    timer7(x + 1, l);
                }
            }
        }.start();
        return true;
    }

    private void on_refresh() {
        //TODO : On refresh kya karna hai???


        LoadData l = new LoadData();
        l.setContext(thisContext);

        JSONObject loginR = l.loginResponseJSON;
        try{
            String[] c_list = new String[loginR.getJSONArray("complaint_list").length()];
            for(int i = 0 ; i < loginR.getJSONArray("complaint_list").length(); i ++ )
                c_list[i] = loginR.getJSONArray("complaint_list").getString(i);

            l.get_complaint_details_request(c_list);
            timercomplaint(1, l, 9, c_list);
            l.flag[9] = false;
            Toast.makeText(MainActivity.this,"Data Populated", Toast.LENGTH_LONG).show();

        }catch(Exception e){e.printStackTrace();}



        //Intent intent = new Intent(thisContext, LoginActivity.class);
        //startActivity(intent);
    }


    //REFRESH SHIT

    public boolean timercomplaint(final int x, final LoadData l, final int whichflag, final String[] c_list){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    //Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[whichflag]){
                    if(whichflag == 9)
                    {
                        Log.i("gaand", "sajdas");
                        try {
                            JSONObject cdr = l.complaintDetailsResponse;
                            Log.i("gaandu","sajdaus");

                            if (cdr.getBoolean("success")) {
                                Log.i("gaandusuc","sajdaussuc");

                                JSONArray comparr = (JSONArray) cdr.get("complaints");
                                Log.i("sandj","sadjsakdas");
                                l.complaintDetailsArray = new JSONObject[comparr.length()];
                                for ( int i = 0 ; i < comparr.length(); i ++ )
                                {
                                    l.complaintDetailsArray[i] = comparr.getJSONObject(i);
                                }

                                //Calling notifications timer
                                l.get_notifications_request(c_list);
                                timernotifications(1, l, 10);
                                l.flag[10] = false;

                            }
                            //list.add(new fraud("Title ka naam kya hona chaiyeh?? Shreyan kya hai.\n New line karke kya milega tujhe? " + i, "Lodger " + i, "bla"));
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                } else {
                    timercomplaint(x + 1, l, whichflag, c_list);
                }
            }
        }.start();
        return true;
    }


    public boolean timernotifications(final int x, final LoadData l, final int whichflag) {

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                if (x == 100) {
                    //Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_LONG).show();
                } else if (l.flag[whichflag]) {
                    if (whichflag == 10) {
                        Log.i("gaand", "sajdas");
                        try {
                            JSONObject cdr = l.notificationsJSON;

                            if (cdr.getBoolean("success")) {

                                JSONArray comparr = (JSONArray) cdr.get("notifications");
                                Log.i("sandj", "sadjsakdas");
                                l.notificationsArray = new JSONObject[comparr.length()];
                                for (int i = 0; i < comparr.length(); i++) {
                                    l.notificationsArray[i] = comparr.getJSONObject(i);
                                }


                            }
                            //list.add(new fraud("Title ka naam kya hona chaiyeh?? Shreyan kya hai.\n New line karke kya milega tujhe? " + i, "Lodger " + i, "bla"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                } else {
                    timernotifications(x + 1, l, whichflag);
                }
            }
        }.start();
        return true;
    }
}
