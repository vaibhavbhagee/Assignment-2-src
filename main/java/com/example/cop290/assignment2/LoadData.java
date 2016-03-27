package com.example.cop290.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//TODO: FLAGS NOT SET AT ALL
//TODO: TO BE ANNOUNCED
import org.json.JSONObject;

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
    public static JSONObject loginResponseJSON;
    public static JSONObject loginResponse;
    public static JSONObject getComplaintsResponse;
    public static JSONObject complaintDetailsResponse;
    public static JSONObject addComplaintResponse;
    public static JSONObject newThreadResponse;
    public static JSONObject newCommentResponse;
    public static JSONObject markResolvedResponse;
    public static JSONObject relodgeHigherResponse;
    public static JSONObject relodgeSameResponse;
    public static JSONObject voteResponse;

    public static Context thisContext = null;


/*
    private String ServerURL = "http://141.8.224.169:8081/api";
*/

    private String ServerURL = "http://cm-system-iitd.herokuapp.com/api";


    public void setContext(Context c)
    {
        thisContext = c;
    }

//flag[0]
   public void login_request(final String un, final String pw) {

        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("username", un);
        jsonParams.put("password", pw);


        final String loginRequest = ServerURL + "/login";
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, loginRequest, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                    try {
                        loginResponseJSON = response;

                        if(response.getBoolean("success"))
                        {
                            flag[0] = true;
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("token", response.getString("token"));
                            editor.commit();
                            token = response.getString("token");
                        }


                    }catch(Exception e){System.out.println("Something went wrong in login");}
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


    public void get_complaints_request(final String uid) {

    /*flag[1]*/

        final String sRequest = ServerURL + "/complaintlist?unique_id="+uid;
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, sRequest, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        getComplaintsResponse = response;

                        System.out.println(getComplaintsResponse);
                             flag[1] = true;
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



    public void get_complaint_details_request( final String[] listOfComplaints ) {
        final String sRequest = ServerURL + "/complaint_details";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("complaint_list", concat(listOfComplaints));

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        complaintDetailsResponse = response;
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

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> head = new HashMap<String, String>();
                head.put("x-access-token", token);
                return head;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }



    /*flag[2]*/

    public void add_complaint_request( final String isCommunity,final String Type, final String Title, final String Description, final String courseID) {
        final String sRequest = ServerURL + "/new_complaint";
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("is_community", isCommunity);
        jsonParams.put("type", Type);
        jsonParams.put("title", Title);
        jsonParams.put("description", Description);
        jsonParams.put("course_id", courseID);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        addComplaintResponse = response;

                        System.out.println(addComplaintResponse);
                        flag[2] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Add_complaint Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }



        /*flag[3]*/
    public void new_thread_request( final String complaintID,final String Title, final String Description) {
        final String sRequest = ServerURL + "/new_thread";
        Map<String, String> params = new HashMap<String, String>();
        params.put("title", Title);
        params.put("description", Description);
        params.put("complaint_id",complaintID);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        newThreadResponse = response;

                        System.out.println(newThreadResponse);
                        flag[3] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("New_Thread Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws  AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    /*flag[4]*/
    public void new_comment_request( final String complaintID,final String threadID, final String postedBy, final String description, final String timestamp) {
        final String sRequest = ServerURL + "/new_comment";
        Map<String, String> params = new HashMap<String, String>();
        params.put("thread_id", threadID);
        params.put("posted_by", postedBy);
        params.put("complaint_id",complaintID);
        params.put("timestamp", timestamp);
        params.put("description", description);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        newCommentResponse = response;

                        System.out.println(newCommentResponse);
                        flag[4] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("New_comment Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws  AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    /*flag[5]*/
    public void mark_resolved_request(final String complaintID) {
        final String sRequest = ServerURL + "/mark_resolved";
        Map<String, String> params = new HashMap<String, String>();
        params.put("complaint_id",complaintID);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        markResolvedResponse = response;

                        System.out.println(markResolvedResponse);
                        flag[5] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Mark_Resolved Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    /*flag[6]*/
    public void relodge_higher_request( final String complaintID) {
        final String sRequest = ServerURL + "/relodge_higher";
        Map<String, String> params = new HashMap<String, String>();
        params.put("complaint_id",complaintID);


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        relodgeHigherResponse = response;

                        System.out.println(relodgeHigherResponse);
                        flag[6] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Relodge_higehr Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    /*flag[7]*/
    public void relodge_same_request( final String complaintID) {
        final String sRequest = ServerURL + "/relodge_same";
        Map<String, String> params = new HashMap<String, String>();
        params.put("complaint_id",complaintID);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest,new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        relodgeSameResponse = response;

                        System.out.println(relodgeSameResponse);
                        flag[7] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Reldoge_same Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    /*flag[8]*/
    public void vote_request( final String complaintID, final String userID, final String UpDown) {
        final String sRequest = ServerURL + "/vote";
        Map<String, String> params = new HashMap<String, String>();
        params.put("complaint_id",complaintID);
        params.put("user_id",userID);
        params.put("type",UpDown);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, sRequest, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    //On valid response
                    public void onResponse(JSONObject response) {

                        voteResponse = response;

                        System.out.println(voteResponse);
                        flag[8] = true;
                    }
                },
                //Launched when server return error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new AlertDialog.Builder(thisContext).setTitle("Error").setMessage("Vote Error: " + error.toString()).setNeutralButton("Close", null).show();

                    }
                }) {

            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                 params.put("x-access-token",token);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(thisContext);
        requestQueue.add(stringRequest);
    }


    public String concat(String [] list_of_string)
    {
        String res = "";
        for( int i = 0; i < list_of_string.length - 1; i ++)
            res = res + list_of_string[i] + ",";
        res = res + list_of_string[list_of_string.length-1];
        return res;
    }

}
