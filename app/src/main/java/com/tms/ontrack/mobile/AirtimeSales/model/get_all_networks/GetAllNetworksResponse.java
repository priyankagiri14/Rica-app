package com.tms.ontrack.mobile.AirtimeSales.model.get_all_networks;

import android.net.Network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllNetworksResponse {

    @SerializedName("networks")
    @Expose
    private List<NeTworks> networks = null;

    public List<NeTworks> getNetworks() {
        return networks;
    }

    public void setNetworks(List<NeTworks> networks) {
        this.networks = networks;
    }

}