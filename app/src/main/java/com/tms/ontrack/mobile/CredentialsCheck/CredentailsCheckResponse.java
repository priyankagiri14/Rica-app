package com.tms.ontrack.mobile.CredentialsCheck;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CredentailsCheckResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("body")
    @Expose
    private Body body;
    @SerializedName("page")
    @Expose
    private Object page;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
