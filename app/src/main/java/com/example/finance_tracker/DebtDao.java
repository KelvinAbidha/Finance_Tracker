package com.example.finance_tracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

/**
 * DebtDao - Defines the database actions for debt records.
 * Cynthia: This provides the methods the rest of the team needs.
 */
@Dao
public interface DebtDao {

    @Insert
    void insert(Debt debt); // For Deqow to save new debts

    @Update
    void update(Debt debt); // For Nesh to update payment status

    @Delete
    void delete(Debt debt); // For removing records

    @Query("SELECT * FROM debts ORDER BY date_created DESC")
    LiveData<List<Debt>> getAllDebts(); // For Kerry to show the list
}