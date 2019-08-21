package com.tms.ontrack.mobile.AirtimeSales.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartCallAgentLogin {

@SerializedName("responseDescription")
@Expose
private String responseDescription;
@SerializedName("accessToken")
@Expose
private String accessToken;
@SerializedName("tokenType")
@Expose
private String tokenType;
@SerializedName("expiresAt")
@Expose
private String expiresAt;
@SerializedName("scope")
@Expose
private String scope;

public String getResponseDescription() {
return responseDescription;
}

public void setResponseDescription(String responseDescription) {
this.responseDescription = responseDescription;
}

public String getAccessToken() {
return accessToken;
}

public void setAccessToken(String accessToken) {
this.accessToken = accessToken;
}

public String getTokenType() {
return tokenType;
}

public void setTokenType(String tokenType) {
this.tokenType = tokenType;
}

public String getExpiresAt() {
return expiresAt;
}

public void setExpiresAt(String expiresAt) {
this.expiresAt = expiresAt;
}

public String getScope() {
return scope;
}

public void setScope(String scope) {
this.scope = scope;
}

}