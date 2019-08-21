package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Batches {
    public String getBatches() {
        return batches;
    }

    public void setBatches(String batches) {
        this.batches = batches;
    }

    @NonNull
    @PrimaryKey
    public String batches;

}
