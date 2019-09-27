package com.tms.ontrack.mobile.Driver.SignUpAgent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

class SignUpBody {

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
    @SerializedName("lastPasswordResetDate")
    @Expose
    private String lastPasswordResetDate;
    @SerializedName("authority")
    @Expose
    private SignUpAuthority authority;
    @SerializedName("attachments")
    @Expose
    private List<Object> attachments = null;
    @SerializedName("parentId")
    @Expose
    private Integer parentId;
    @SerializedName("warehouseId")
    @Expose
    private Integer warehouseId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("profile")
    @Expose
    private SignUpProfile profile;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("parent")
    @Expose
    private SignUpParent parent;
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

    public String getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(String lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public SignUpAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(SignUpAuthority authority) {
        this.authority = authority;
    }

    public List<Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Object> attachments) {
        this.attachments = attachments;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
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

    public SignUpProfile getProfile() {
        return profile;
    }

    public void setProfile(SignUpProfile profile) {
        this.profile = profile;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public SignUpParent getParent() {
        return parent;
    }

    public void setParent(SignUpParent parent) {
        this.parent = parent;
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
