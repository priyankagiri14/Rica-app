package com.tms.ontrack.mobile.OpenCloseBatches.CashHistory;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Serials {

    @NonNull
    public String getSerials() {
        return serials;
    }

    public void setSerials(@NonNull String serials) {
        this.serials = serials;
    }

    @NonNull
    @PrimaryKey
    public String serials;
}
