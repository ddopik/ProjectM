package com.spade.mek.ui.register;

import com.google.gson.annotations.SerializedName;
import com.spade.mek.ui.login.UserModel;

/**
 * Created by Ayman Abouzeid on 8/9/17.
 */

public class RegistrationResponseData {

    @SerializedName("msg")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("user")
    private UserModel userModel;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
