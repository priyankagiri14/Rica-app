package com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("network")
@Expose
private String network;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
@SerializedName("typeCode")
@Expose
private String typeCode;
@SerializedName("minAmount")
@Expose
private Double minAmount;
@SerializedName("maxAmount")
@Expose
private Double maxAmount;
@SerializedName("discountPercentage")
@Expose
private Double discountPercentage;
@SerializedName("retailValue")
@Expose
private Double retailValue;
@SerializedName("pinIndicator")
@Expose
private String pinIndicator;
@SerializedName("smsIndicator")
@Expose
private String smsIndicator;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getNetwork() {
return network;
}

public void setNetwork(String network) {
this.network = network;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getTypeCode() {
return typeCode;
}

public void setTypeCode(String typeCode) {
this.typeCode = typeCode;
}

public Double getMinAmount() {
return minAmount;
}

public void setMinAmount(Double minAmount) {
this.minAmount = minAmount;
}

public Double getMaxAmount() {
return maxAmount;
}

public void setMaxAmount(Double maxAmount) {
this.maxAmount = maxAmount;
}

public Double getDiscountPercentage() {
return discountPercentage;
}

public void setDiscountPercentage(Double discountPercentage) {
this.discountPercentage = discountPercentage;
}

public Double getRetailValue() {
return retailValue;
}

public void setRetailValue(Double retailValue) {
this.retailValue = retailValue;
}

public String getPinIndicator() {
return pinIndicator;
}

public void setPinIndicator(String pinIndicator) {
this.pinIndicator = pinIndicator;
}

public String getSmsIndicator() {
return smsIndicator;
}

public void setSmsIndicator(String  smsIndicator) {
this.smsIndicator = smsIndicator;
}

}