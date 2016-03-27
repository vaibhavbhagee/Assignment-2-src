package com.example.cop290.assignment2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aayan Kumar on 27-03-2016.
 */
public class ParseListOfComplaintsJSON {
    public boolean success;
    public String[] complaintlist;
    public ParseListOfComplaintsJSON(String inputString) throws JSONException{
        JSONObject responseJSON = new JSONObject(inputString);

        this.success = responseJSON.getBoolean("success");

        if(this.success)
        {
            JSONArray list_t = responseJSON.getJSONArray("complaintlist");

            complaintlist = new String[list_t.length()];
            for( int i = 0 ; i < list_t.length() ; i ++ )
                complaintlist[i] = list_t.getString(i);

        }
        System.out.println(inputString);

    }
}
