package com.ubits.payflow.payflow_network.DriverBatchesGet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Warehouse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
