package com.tms.ontrack.mobile.OpenCloseBatches.CashHistory;

import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Serials.class},version = 1)
public abstract class AppDatabaseSerials extends RoomDatabase {

    public abstract SerialsInterface serialsInterface();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }


    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
