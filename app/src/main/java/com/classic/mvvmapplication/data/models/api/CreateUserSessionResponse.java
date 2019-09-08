package com.classic.mvvmapplication.data.models.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateUserSessionResponse {

    public CreateUserSessionResponse() {
    }

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("session_id")
    @Expose
    private String sessionId;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
