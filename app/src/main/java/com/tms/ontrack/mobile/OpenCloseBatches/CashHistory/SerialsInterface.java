package com.tms.ontrack.mobile.OpenCloseBatches.CashHistory;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface SerialsInterface {

    @Insert
    void insertSerials(Serials... serials);

    @Query("Select * from Serials")
    List<Serials> serialstabledata();

    @Query("Select * from Serials WHERE serials LIKE:query")
    List<Serials> serialscount(String query);

    @Query("Delete FROM Serials Where serials LIKE:query")
    void deleteSerials(String query);
}
