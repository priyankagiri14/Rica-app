package com.tms.ontrack.mobile.Driver.DriverAttendance.model.driverattendancephoto;

import java.util.HashMap;
import java.util.Map;

public class Attachment {

    private Integer id;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
