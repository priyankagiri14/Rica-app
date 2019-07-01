package com.ubits.payflow.payflow_network.model;

public class ProcessBatch {

    private int id;
    private String barCode;

    public ProcessBatch() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
