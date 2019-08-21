package com.tms.ontrack.mobile.AgentBatchesGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Body {

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
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("totalValue")
    @Expose
    private Double totalValue;
    @SerializedName("count")
    @Expose
    private Integer count;

    public boolean isValueSim() {
        return valueSim;
    }

    public void setValueSim(boolean valueSim) {
        this.valueSim = valueSim;
    }

    @SerializedName("valueSim")
    @Expose
    private boolean valueSim;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public boolean ischecked = false;
}
