package com.tms.ontrack.mobile.AgentBatchesGet;

public class MyPojo {

    String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getBatches() {
        return batches;
    }

    public void setBatches(String[] batches) {
        this.batches = batches;
    }

    String[] batches;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    String reason = "";
}
