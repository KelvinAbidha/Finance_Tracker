package com.example.finance_tracker;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

// This tells Room which tables and converters to include in the database
@Database(entities = {Debt.class, User.class}, version = 1, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    // These link your DAOs to the database
    public abstract DebtDao debtDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;

    // The Singleton pattern ensures only one database connection exists
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "finance_tracker_db")
                            .fallbackToDestructiveMigration() // Useful during development
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}