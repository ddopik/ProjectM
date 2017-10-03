package com.spade.mek.ui.more.contact_us.presenter;

import android.content.Context;
import android.os.AsyncTask;

import com.spade.mek.R;
import com.spade.mek.network.ApiHelper;
import com.spade.mek.realm.RealmDbHelper;
import com.spade.mek.realm.RealmDbImpl;
import com.spade.mek.ui.login.User;
import com.spade.mek.ui.more.contact_us.view.SendMessageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ayman Abouzeid on 6/27/17.
 */

public class SendMessagePresenterImpl implements SendMessagePresenter {

    private Context mContext;
    private RealmDbHelper realmDbHelper;
    private SendMessageView sendMessageView;
    private String userName, city, email, subject, message;

    public SendMessagePresenterImpl(Context mContext) {
        this.mContext = mContext;
        realmDbHelper = new RealmDbImpl();
    }

    @Override
    public void setView(SendMessageView view) {
        this.sendMessageView = view;
    }

    @Override
    public void shareItem(String url) {

    }

    @Override
    public User getUser(String userId) {
        return realmDbHelper.getUser(userId);
    }

    @Override
    public void sendMessage(String userName, String city, String subject, String emailAddress, String message) {
        this.userName = userName;
        this.city = city;
        this.email = emailAddress;
        this.subject = subject;
        this.message = message;
        new CreateJsonRequestObject().execute();
    }

    private void sendMessage(JSONObject jsonObjectRequest) {
        sendMessageView.showLoading();
        ApiHelper.sendMessage(jsonObjectRequest, new ApiHelper.SendMessageCallBacks() {
            @Override
            public void onMessageSent(boolean isSuccess) {
                sendMessageView.hideLoading();
                if (isSuccess) {
                    sendMessageView.finish();
                    sendMessageView.navigateToConfirmationScreen();
                } else {
                    sendMessageView.onError(R.string.something_wrong);
                }
            }

            @Override
            public void onMessageSentFailed(String error) {
                sendMessageView.hideLoading();
                sendMessageView.onError(error);
            }
        });
    }

    private class CreateJsonRequestObject extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject requestJsonObject = null;
            try {
                requestJsonObject = new JSONObject();
                requestJsonObject.put("name", userName);
                requestJsonObject.put("city", city);
                requestJsonObject.put("email", email);
                requestJsonObject.put("subject", subject);
                requestJsonObject.put("message", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return requestJsonObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            sendMessage(jsonObject);
        }
    }
}
