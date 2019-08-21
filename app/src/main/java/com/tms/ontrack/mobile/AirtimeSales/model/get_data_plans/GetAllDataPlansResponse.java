package com.tms.ontrack.mobile.AirtimeSales.model.get_data_plans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllDataPlansResponse {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("description")
@Expose
private String description;
@SerializedName("productTypes")
@Expose
private List<ProductType> productTypes = null;

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

public List<ProductType> getProductTypes() {
return productTypes;
}

public void setProductTypes(List<ProductType> productTypes) {
this.productTypes = productTypes;
}

}