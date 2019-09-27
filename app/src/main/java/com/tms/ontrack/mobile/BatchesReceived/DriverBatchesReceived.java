package com.tms.ontrack.mobile.BatchesReceived;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class DriverBatchesReceived {

    @NonNull
    @PrimaryKey
    public String driverbatchesreceived;

    @NonNull
    public String getDriverBathcesreceived() {
        return driverbatchesreceived;
    }

    public void setDriverBathcesreceived(@NonNull String driverbathcesreceived) {
        this.driverbatchesreceived = driverbathcesreceived;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    @Ignore
    public boolean ischecked = false;

}

