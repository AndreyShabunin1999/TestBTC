package com.example.testbtc;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface UserDAO {

    @Query("select balance from User where user_id = :user_id")
    int getUserBalance(int user_id);
}

