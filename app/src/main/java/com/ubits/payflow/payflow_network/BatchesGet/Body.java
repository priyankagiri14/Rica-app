package com.ubits.payflow.payflow_network.BatchesGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ubits.payflow.payflow_network.BatchesGet.AssignedTo;

public class Body {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("batchNo")
    @Expose
    private String batchNo;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("assignedToId")
    @Expose
    private Integer assignedToId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("warehouse")
    @Expose
    private Warehouse warehouse;
    @SerializedName("assignedTo")
    @Expose
    private AssignedTo assignedTo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getAssignedToId() {
        return assignedToId;
    }

    public void setAssignedToId(Integer assignedToId) {
        this.assignedToId = assignedToId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public AssignedTo getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedTo assignedTo) {
        this.assignedTo = assignedTo;
    }

}
