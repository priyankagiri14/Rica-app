package com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.team_attendance;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team_Attendance_Response {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

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
}
