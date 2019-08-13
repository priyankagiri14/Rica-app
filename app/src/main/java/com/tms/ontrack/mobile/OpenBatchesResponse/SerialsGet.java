package com.tms.ontrack.mobile.OpenBatchesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SerialsGet {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("body")
    @Expose
    private List<Body> body = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Body> getBody() {
        return body;
    }

    public void setBody(List<Body> body) {
        this.body = body;
    }
}
