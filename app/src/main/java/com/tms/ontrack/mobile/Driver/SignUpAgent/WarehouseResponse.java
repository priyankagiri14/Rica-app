package com.tms.ontrack.mobile.Driver.SignUpAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WarehouseResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("body")
    @Expose
    private List<WarehouseBody> body = null;
    @SerializedName("page")
    @Expose
    private Page page;

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

    public List<WarehouseBody> getBody() {
        return body;
    }

    public void setBody(List<WarehouseBody> body) {
        this.body = body;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
