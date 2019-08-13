package com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto;


import java.util.HashMap;
import java.util.Map;

public class UploadedFile  {

    private Boolean success;
    private String message;
    private Body body;
    private Object page;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}