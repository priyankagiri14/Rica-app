package com.tms.ontrack.mobile.Driver.Driver_Dashboard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ValueWalletResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("body")
    @Expose
    private Integer body;
    @SerializedName("page")
    @Expose
    private Object page;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getBody() {
        return body;
    }

    public void setBody(Integer body) {
        this.body = body;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }
}
