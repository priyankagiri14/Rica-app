package com.ubits.payflow.payflow_network.FetchStocks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class StockData {
    @SerializedName("stock_id")
    @Expose
    private String stockId;
    @SerializedName("created")
    @Expose
    private String created;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

}
