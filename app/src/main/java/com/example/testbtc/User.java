package com.example.testbtc;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "User")
public class User {

    @ColumnInfo(name="user_id")
    @PrimaryKey(autoGenerate = true)
    Integer id;

    @ColumnInfo(name="name")
    String name;

    @ColumnInfo(name = "balance")
    Integer balance;

    @Ignore
    public User() {

    }

    public User(String name, Integer balance) {
        this.name = name;
        this.balance = balance;
        this.id = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getBalance() {
        return balance;
    }
}
