package com.tms.ontrack.mobile.AirtimeSales.model.get_all_networks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NeTworks {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
