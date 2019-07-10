package com.ubits.payflow.payflow_network.FetchStocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class AssignedByMe {
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("assigned_by")
    @Expose
    private AssignedBy_ assignedBy;
    @SerializedName("is_accepted_by_receiver")
    @Expose
    private String isAcceptedByReceiver;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("stock_data")
    @Expose
    private StockData_ stockData;
    @SerializedName("assigned_to")
    @Expose
    private AssignedTo_ assignedTo;

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AssignedBy_ getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(AssignedBy_ assignedBy) {
        this.assignedBy = assignedBy;
    }

    public String getIsAcceptedByReceiver() {
        return isAcceptedByReceiver;
    }

    public void setIsAcceptedByReceiver(String isAcceptedByReceiver) {
        this.isAcceptedByReceiver = isAcceptedByReceiver;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public StockData_ getStockData() {
        return stockData;
    }

    public void setStockData(StockData_ stockData) {
        this.stockData = stockData;
    }

    public AssignedTo_ getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedTo_ assignedTo) {
        this.assignedTo = assignedTo;
    }
}
