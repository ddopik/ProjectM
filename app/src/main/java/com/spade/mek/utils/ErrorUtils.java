package com.spade.mek.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aymanabouzeid on 12/09/17.
 */

public class ErrorUtils {


    public static String getErrors(String errorResponse) {
        String error = "";
        try {
            JSONObject jsonObject = new JSONObject(errorResponse);
            JSONArray jsonArray = jsonObject.getJSONObject("msg").getJSONArray("errors");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (i == jsonArray.length() - 1) {
                    error += jsonArray.getString(i);
                } else {
                    error += jsonArray.getString(i) + " , ";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return error;
    }
}

