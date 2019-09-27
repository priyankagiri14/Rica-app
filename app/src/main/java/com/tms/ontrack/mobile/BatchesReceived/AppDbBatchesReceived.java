package com.tms.ontrack.mobile.BatchesReceived;


import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {DriverBatchesReceived.class}, version = 1)
public abstract class AppDbBatchesReceived extends RoomDatabase {

    public abstract DriverBatchesReceivedInterface driverBatchesReceivedInterface();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }


    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
