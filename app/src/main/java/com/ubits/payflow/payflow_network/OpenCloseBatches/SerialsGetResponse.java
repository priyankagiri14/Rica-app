package com.ubits.payflow.payflow_network.OpenCloseBatches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SerialsGetResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("body")
    @Expose
    private String body;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}

