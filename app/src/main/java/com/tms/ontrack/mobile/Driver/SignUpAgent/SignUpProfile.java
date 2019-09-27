package com.tms.ontrack.mobile.Driver.SignUpAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpProfile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idType")
    @Expose
    private String idType;
    @SerializedName("idNo")
    @Expose
    private String idNo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobileNo")
    @Expose
    private String mobileNo;
    @SerializedName("altMobileNo")
    @Expose
    private String altMobileNo;
    @SerializedName("ricaUser")
    @Expose
    private String ricaUser;
    @SerializedName("ricaPassword")
    @Expose
    private String ricaPassword;
    @SerializedName("ricaGroup")
    @Expose
    private String ricaGroup;
    @SerializedName("activationCom")
    @Expose
    private Double activationCom;
    @SerializedName("dailyRate")
    @Expose
    private Double dailyRate;
    @SerializedName("ogr")
    @Expose
    private Double ogr;
    @SerializedName("simCost")
    @Expose
    private Double simCost;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAltMobileNo() {
        return altMobileNo;
    }

    public void setAltMobileNo(String altMobileNo) {
        this.altMobileNo = altMobileNo;
    }

    public String getRicaUser() {
        return ricaUser;
    }

    public void setRicaUser(String ricaUser) {
        this.ricaUser = ricaUser;
    }

    public String getRicaPassword() {
        return ricaPassword;
    }

    public void setRicaPassword(String ricaPassword) {
        this.ricaPassword = ricaPassword;
    }

    public String getRicaGroup() {
        return ricaGroup;
    }

    public void setRicaGroup(String ricaGroup) {
        this.ricaGroup = ricaGroup;
    }

    public Double getActivationCom() {
        return activationCom;
    }

    public void setActivationCom(Double activationCom) {
        this.activationCom = activationCom;
    }

    public Double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(Double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public Double getOgr() {
        return ogr;
    }

    public void setOgr(Double ogr) {
        this.ogr = ogr;
    }

    public Double getSimCost() {
        return simCost;
    }

    public void setSimCost(Double simCost) {
        this.simCost = simCost;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
