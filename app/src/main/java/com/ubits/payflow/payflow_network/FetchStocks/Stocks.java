package com.ubits.payflow.payflow_network.FetchStocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stocks {
    @SerializedName("inhand")
    @Expose
    private List<Inhand> inhand = null;
    @SerializedName("assigned_by_me")
    @Expose
    private List<AssignedByMe> assignedByMe = null;

    public List<Inhand> getInhand() {
        return inhand;
    }

    public void setInhand(List<Inhand> inhand) {
        this.inhand = inhand;
    }

    public List<AssignedByMe> getAssignedByMe() {
        return assignedByMe;
    }

    public void setAssignedByMe(List<AssignedByMe> assignedByMe) {
        this.assignedByMe = assignedByMe;
    }
}
