package com.ubits.payflow.payflow_network.mDataObject;

/**
 * Created by sauda on 2017/08/29.
 */

public class statementdata {

    int id;
    String batch,date,description, Reference, StatDate, StatID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getstatement() {
        return batch;
    }
    // Reference

    public String getReference() {return Reference;}

    public void setReference(String Reference) {this.Reference = Reference;}

    // Stat Date

    public String getStatDate() {return StatDate;}

    public void setStatDate(String StatDate) {this.StatDate = StatDate;}

    //Stat ID

    public String getStatID() {return StatID;}

    public void setStatID(String StatID) {this.StatID = StatID;}
}
