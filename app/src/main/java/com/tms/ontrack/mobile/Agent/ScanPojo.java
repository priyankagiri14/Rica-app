package com.tms.ontrack.mobile.Agent;

public class ScanPojo {
        public String[] getBatches() {
            return batches;
        }

        public void setBatches(String[] batches) {
            this.batches = batches;
        }

        String[] batches;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getFuncity() {
        return funcity;
    }

    public void setFuncity(String funcity) {
        this.funcity = funcity;
    }

    String network;
    String idnum;
    String fname;
    String lname;
    String address;
    String suburb;
    String postalcode;
    String funcity;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    String region;
}
