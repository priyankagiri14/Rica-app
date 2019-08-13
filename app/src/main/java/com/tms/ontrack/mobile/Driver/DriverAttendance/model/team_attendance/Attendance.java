package com.tms.ontrack.mobile.Driver.DriverAttendance.model.team_attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendance {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
