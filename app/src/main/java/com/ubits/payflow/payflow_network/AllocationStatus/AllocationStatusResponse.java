package com.ubits.payflow.payflow_network.AllocationStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllocationStatusResponse {
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(String message) {
        this.message = message;
    }

}
