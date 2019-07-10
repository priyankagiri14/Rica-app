package com.ubits.payflow.payflow_network.FetchStocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Inhand {
    @SerializedName("assignment_id")
    @Expose
    private String assignmentId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("assigned_by")
    @Expose
    private AssignedBy assignedBy;
    @SerializedName("is_accepted_by_receiver")
    @Expose
    private String isAcceptedByReceiver;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("stock_data")
    @Expose
    private StockData stockData;
    @SerializedName("assigned_to")
    @Expose
    private AssignedTo assignedTo;

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

    public AssignedBy getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(AssignedBy assignedBy) {
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

    public StockData getStockData() {
        return stockData;
    }

    public void setStockData(StockData stockData) {
        this.stockData = stockData;
    }

    public AssignedTo getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedTo assignedTo) {
        this.assignedTo = assignedTo;
    }

}
