package com.tms.ontrack.mobile.Driver.SignUpAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpWarehouse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isSimManagement")
    @Expose
    private Boolean isSimManagement;

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

    public Boolean getIsSimManagement() {
        return isSimManagement;
    }

    public void setIsSimManagement(Boolean isSimManagement) {
        this.isSimManagement = isSimManagement;
    }
}
