package com.tms.ontrack.mobile.DriverBatchesGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BatchesGetResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("body")
    @Expose
    private List<Body> body = null;
    @SerializedName("page")
    @Expose
    private Page page;

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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
