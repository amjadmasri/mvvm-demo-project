package com.classic.mvvmapplication.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenericPostRequestResponse {

    public GenericPostRequestResponse() {
    }

    @Expose
    @SerializedName("status_message")
    private String statusMessage;
    @Expose
    @SerializedName("status_code")
    private int statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
