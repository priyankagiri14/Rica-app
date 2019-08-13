package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BatchesInterface {
    @Insert
    void insertBatches(Batches... batches);

    @Query("Select * from Batches WHERE batches LIKE :input")
    List<Batches> batchestabledata(String input);



}
