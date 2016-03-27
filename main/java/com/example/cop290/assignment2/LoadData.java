package com.example.cop290.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//TODO: FLAGS NOT SET AT ALL
//TODO: TO BE ANNOUNCED
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aayan Kumar on 26-03-2016.
 */
public class LoadData extends Activity {


    public static final String SharedPref = "MahPrefs";
    SharedPreferences sharedpreferences;

    public static boolean[] flag = new boolean[11];

    public static String token;
    public static String LoginResponseJSON;
    public static String loginResponse;
    public static String getComplaintsResponse;
    public static String complaintDetailsResponse;
    public static String addComplaintResponse;
    public static String newThreadResponse;
    public static String newCommentResponse;
    public static String markResolvedResponse;
    public static String relodgeHigherResponse;
    public static String relodgeSameResponse;
    public static String voteResponse;

    public static Context thisContext = null;


/*
    private String ServerURL = "http://141.8.224.169:8081/api";
*/

    private String ServerURL = "http://cm-system-iitd.herokuapp.com/api";


    public void setContext(Context c)
    {
        thisContext = c;
    }

    public void login_request(final String un, final String pw) {
        final String loginRequest = ServerURL + "/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                    try {
                        loginResponse = response;
                        ParseLoginJSON p = new ParseLoginJSON(response);
                        token = p.token;
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("token", token);
                        editor.commit();
                        System.out.println("ServerLoginResponseReceivedSucces value: " + p.success + ":" + loginResponse);

                        if(p.success == true) {
                            /*System.out.println("Sucess!" + p.success);*/
                            flag[0] = true;
                        }


                    }catch(Exception e){}
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Login_Request Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", un);
                params.put("password", pw);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    public void get_complaints_request(final String token,final String uid) {
        final String sRequest = ServerURL + "/complaintlist?unique_id="+uid;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        getComplaintsResponse = response;
                        System.out.println(getComplaintsResponse);
                             flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Get_complaints Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {


        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }



   /* public void get_complaint_details_request( final String[] listOfComplaints ) {
        final String sRequest = ServerURL + "/complaintdetails";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        complaintDetailsResponse = response;
                        System.out.println(complaintDetailsResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Complaint_Details Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

                protected Map<String, String[]> getParams() {
                Map<String, String[]> params = new HashMap<String, String[]>();
                params.put("complaint_list", listOfComplaints);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }
*/


    public void add_complaint_request( final String isCommunity,final String Type, final String Title, final String Description, final String courseID) {
        final String sRequest = ServerURL + "/new_complaint";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        addComplaintResponse = response;
                        System.out.println(addComplaintResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Add_complaint Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("is_community", isCommunity);
                params.put("type", Type);
                params.put("title", Title);
                params.put("description", Description);
                params.put("course_id",courseID);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }




    public void new_thread_request( final String complaintID,final String Title, final String Description) {
        final String sRequest = ServerURL + "/new_thread";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        newThreadResponse = response;
                        System.out.println(newThreadResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("New_Thread Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", Title);
                params.put("description", Description);
                params.put("complaint_id",complaintID);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }





    public void new_comment_request( final String complaintID,final String threadID, final String postedBy, final String description, final String timestamp) {
        final String sRequest = ServerURL + "/new_comment";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        newCommentResponse = response;
                        System.out.println(newCommentResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("New_comment Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("thread_id", threadID);
                params.put("posted_by", postedBy);
                params.put("complaint_id",complaintID);
                params.put("timestamp", timestamp);
                params.put("description", description);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }




    public void mark_resolved_request( final String complaintID) {
        final String sRequest = ServerURL + "/mark_resolved";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        markResolvedResponse = response;
                        System.out.println(markResolvedResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Mark_Resolved Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaint_id",complaintID);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }



    public void relodge_higher_request( final String complaintID) {
        final String sRequest = ServerURL + "/relodge_higher";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        relodgeHigherResponse = response;
                        System.out.println(relodgeHigherResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Relodge_higehr Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaint_id",complaintID);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }




    public void relodge_same_request( final String complaintID) {
        final String sRequest = ServerURL + "/relodge_same";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        relodgeSameResponse = response;
                        System.out.println(relodgeSameResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Reldoge_same Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaint_id",complaintID);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }





    public void vote_request( final String complaintID, final String userID, final String UpDown) {
        final String sRequest = ServerURL + "/vote";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sRequest,
                new Response.Listener<String>() {
                    @Override
                    //On valid response
                    public void onResponse(String response) {

                        voteResponse = response;
                        System.out.println(voteResponse);
                        flag[0] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Vote Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("complaint_id",complaintID);
                params.put("user_id",userID);
                params.put("type",UpDown);
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }



}
