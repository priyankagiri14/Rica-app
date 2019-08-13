package com.tms.ontrack.mobile.AgentBatchesGet;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

@Database(entities = {Batches.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract  BatchesInterface batchesInterface();

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }


    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
