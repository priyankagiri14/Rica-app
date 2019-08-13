package com.tms.ontrack.mobile.Agent.AttendanceGetResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Body {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("markedById")
    @Expose
    private Integer markedById;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("confirmation")
    @Expose
    private Boolean confirmation;
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("markedBy")
    @Expose
    private MarkedBy markedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMarkedById() {
        return markedById;
    }

    public void setMarkedById(Integer markedById) {
        this.markedById = markedById;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(Boolean confirmation) {
        this.confirmation = confirmation;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MarkedBy getMarkedBy() {
        return markedBy;
    }

    public void setMarkedBy(MarkedBy markedBy) {
        this.markedBy = markedBy;
    }

}
