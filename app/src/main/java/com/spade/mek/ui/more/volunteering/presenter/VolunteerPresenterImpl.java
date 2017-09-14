package com.spade.mek.ui.more.volunteering.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.cart.view.UserDataView;
import com.spade.mek.ui.login.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class VolunteerPresenterImpl implements VolunteerPresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private UserDataView userDataView;

    public VolunteerPresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(UserDataView view) {
        userDataView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void volunteer(String firstName, String lastName, String phoneNumber,
                          String emailAddress, String address, String eventId) {
        new CreateJsonRequestObject().execute(firstName, lastName, phoneNumber, emailAddress, address, eventId);
    }

    private void submitVolunteering(JSONObject jsonObject) {
        userDataView.showLoading();
        ApiHelper.volunteerToEvent(jsonObject, new ApiHelper.VolunteeringCallBacks() {
            @Override
            public void onVolunteerSuccess() {
                userDataView.hideLoading();
                userDataView.navigateToConfirmationScreen();
            }

            @Override
            public void onVolunteerFailed(String error) {
                userDataView.hideLoading();
                userDataView.onError(error);
            }
        });
    }

    private class CreateJsonRequestObject extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject requestJsonObject = null;
            try {
                requestJsonObject = new JSONObject();
                requestJsonObject.put("first_name", params[0]);
                requestJsonObject.put("last_name", params[1]);
                requestJsonObject.put("phone", params[2]);
                requestJsonObject.put("email", params[3]);
                requestJsonObject.put("address", params[4]);
                requestJsonObject.put("event_id", params[5]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            submitVolunteering(jsonObject);
        }
    }

}
