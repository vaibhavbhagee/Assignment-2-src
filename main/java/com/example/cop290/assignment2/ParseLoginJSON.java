package com.example.cop290.assignment2;

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
        JSONObject responseJSON = new JSONObject(inputString);
        this.success = responseJSON.getBoolean("success");

        if(this.success == true)
        {
            this.token = responseJSON.getString("token");
            this.unique_id = responseJSON.getString("unique_id");
            this.name = responseJSON.getString("name");
            this.department = responseJSON.getString("department");
            this.contact_info = responseJSON.getString("contact_info");
            JSONArray tags_t = responseJSON.getJSONArray("tags");
            JSONArray course_list_t = responseJSON.getJSONArray("course_list");
            JSONArray complaint_list_t = responseJSON.getJSONArray("complaint_list");

            tags = new String[tags_t.length()];
            for(int i = 0; i < tags_t.length(); i ++ )
            {
                tags[i] = tags_t.getString(i);
            }

            course_list = new String[course_list_t.length()];
            for(int i = 0; i < course_list_t.length(); i ++ )
            {
                course_list[i] = course_list_t.getString(i);
            }

            complaint_list = new String[complaint_list_t.length()];
            for(int i = 0; i < complaint_list_t.length(); i ++ )
            {
                complaint_list[i] = complaint_list_t.getString(i);
            }
        }
        System.out.println(inputString);
    }
}
