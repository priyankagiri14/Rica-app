package com.ubits.payflow.payflow_network.Driver.DriverAttendance.model.get_stores;

import java.util.HashMap;
import java.util.Map;

public class Page {
    private Integer numberOfElements;
    private Integer totalElements;
    private Integer totalPages;
    private Integer size;
    private Integer pageNumber;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getNumberOfElements(){
    return numberOfElements;
}

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}