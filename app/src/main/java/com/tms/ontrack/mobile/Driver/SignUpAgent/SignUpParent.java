package com.tms.ontrack.mobile.Driver.SignUpAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SignUpParent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("customerId")
    @Expose
    private String customerId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("authority")
    @Expose
    private SignUpAuthority_ authority;
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("balance")
    @Expose
    private Double balance;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("profile")
    @Expose
    private SignUpProfile_ profile;
    @SerializedName("address")
    @Expose
    private SignUpAddress_ address;
    @SerializedName("warehouse")
    @Expose
    private SignUpWarehouse warehouse;
    @SerializedName("paymentAccounts")
    @Expose
    private List<Object> paymentAccounts = null;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public SignUpAuthority_ getAuthority() {
        return authority;
    }

    public void setAuthority(SignUpAuthority_ authority) {
        this.authority = authority;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public SignUpProfile_ getProfile() {
        return profile;
    }

    public void setProfile(SignUpProfile_ profile) {
        this.profile = profile;
    }

    public SignUpAddress_ getAddress() {
        return address;
    }

    public void setAddress(SignUpAddress_ address) {
        this.address = address;
    }

    public SignUpWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(SignUpWarehouse warehouse) {
        this.warehouse = warehouse;
    }

    public List<Object> getPaymentAccounts() {
        return paymentAccounts;
    }

    public void setPaymentAccounts(List<Object> paymentAccounts) {
        this.paymentAccounts = paymentAccounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
