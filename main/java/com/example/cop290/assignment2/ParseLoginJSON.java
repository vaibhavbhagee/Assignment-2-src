package com.example.cop290.assignment2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aayan Kumar on 26-03-2016.
 */
public class ParseLoginJSON {

    public boolean success;
    public String token;
    public String unique_id;
    public String name;
    public String department;
    public String contact_info;
    public String[] tags;
    public String[] course_list;
    public String[] complaint_list;

    public ParseLoginJSON(String inputString) throws JSONException
    {
        //JSONObject responseJSON = new JSONObject(inputString);
        //this.success = responseJSON.getBoolean("success");
        //System.out.print(this.success);
        //Log.i("bhaggu","sabdjiandsdjkdshcodscdsojcdsdsncjkdcd" +);
        /*if(this.success == true)
        {
            this.token = responseJSON.getString("token");
            System.out.println("tokenbc");
            this.unique_id = responseJSON.getString("unique_id");
            System.out.println("idbc");
            this.name = responseJSON.getString("name");
            System.out.println("namebc");
            this.department = responseJSON.getString("department");
            System.out.println("deptbc");
            this.contact_info = responseJSON.getString("contact_info");
            System.out.println("contactbc");
            *//*this.tags = (String[])responseJSON.get("tags");
            System.out.println("tagsbc");
            this.course_list = (String[])responseJSON.get("course_list");
            System.out.println("coursebc");
            this.complaint_list = (String[])responseJSON.get("complaint_list");
            System.out.println("complaintsbc");
*//*

        }*/
        Log.i("bhaggu","eyyafyallayokul" + inputString);
    }
}
