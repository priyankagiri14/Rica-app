package com.ubits.payflow.payflow_network.model;

public class PlaceAutocomplete {

    public CharSequence placeId;
    public CharSequence description;
    public double lat;
    public double lng;

    public PlaceAutocomplete() {

    }

    public CharSequence getPlaceId() {
        return placeId;
    }

    public void setPlaceId(CharSequence placeId) {
        this.placeId = placeId;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return description.toString();
    }
}