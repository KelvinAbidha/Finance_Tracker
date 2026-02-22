package com.example.finance_tracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * UserDao - Handles database operations for the User entity.
 * Cynthia: This allows Daniel to fetch the username for the UI.
 */
@Dao
public interface UserDao {

    // OnConflictStrategy.REPLACE ensures that updating a profile overwrites the old one
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM users LIMIT 1")
    LiveData<User> getUser();
}