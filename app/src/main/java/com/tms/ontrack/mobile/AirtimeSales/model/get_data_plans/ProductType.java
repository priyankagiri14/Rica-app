package com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductType {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("code")
@Expose
private String code;
@SerializedName("description")
@Expose
private String description;
@SerializedName("fixedAmount")
@Expose
private Boolean fixedAmount;
@SerializedName("products")
@Expose
private List<Product> products = null;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Boolean getFixedAmount() {
return fixedAmount;
}

public void setFixedAmount(Boolean fixedAmount) {
this.fixedAmount = fixedAmount;
}

public List<Product> getProducts() {
return products;
}

public void setProducts(List<Product> products) {
this.products = products;
}

}