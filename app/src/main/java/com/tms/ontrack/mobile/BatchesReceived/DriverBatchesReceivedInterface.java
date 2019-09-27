package com.tms.ontrack.mobile.BatchesReceived;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DriverBatchesReceivedInterface {

    @Insert
    void insertDriverBatchesReceived(DriverBatchesReceived... driverbatchesreceived);

    @Query("Select * from DriverBatchesReceived")
    List<DriverBatchesReceived> driverbatchereceivedstabledata();

    @Query("Select driverbatchesreceived from DriverBatchesReceived WHERE driverbatchesreceived LIKE:query")
    List<DriverBatchesReceived> count(String query);

    @Query("Delete FROM DriverBatchesReceived Where driverbatchesreceived LIKE:query")
    void deleteDriverBatchesReceived(String query);

    @Query("Delete FROM DriverBatchesReceived")
    public void deleteDriverData();
}
