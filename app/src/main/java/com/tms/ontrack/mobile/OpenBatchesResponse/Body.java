package com.tms.ontrack.mobile.OpenBatchesResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Body {
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("status")
    @Expose
    private String status;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
