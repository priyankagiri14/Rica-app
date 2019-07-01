package com.ubits.payflow.payflow_network.mDataObject;

/**
 * Created by sauda on 2017/08/13.
 */

public class payflowdata {

    int id;
    String batch,date,description, Reference, StatDate, StatID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}


}
