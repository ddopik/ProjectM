package com.spade.mek.utils;

import android.content.Context;

import com.androidnetworking.error.ANError;
import com.spade.mek.R;
import com.spade.mek.application.MekApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aymanabouzeid on 12/09/17.
 */

public class ErrorUtils {


    public static String getErrors(ANError anError) {
        String error = "";
        if (anError.getErrorBody() != null) {
            try {
                JSONObject jsonObject = new JSONObject(anError.getErrorBody());
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
        } else {
            if (error.hashCode() == 0) { // todo in case unKnown or no NetWork connection
                error = MekApplication.mApplication.getResources().getString(R.string.no_internet);
            } else {
                error = anError.getMessage();
            }

        }
        return error;
    }
}

