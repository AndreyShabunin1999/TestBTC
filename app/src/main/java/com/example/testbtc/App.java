package com.example.testbtc;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class App extends Application {

    public static App instance;

    private UserDatabase userDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        RoomDatabase.Callback callback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                db.execSQL("INSERT INTO User (name, balance) VALUES ('Andrey', 15)");
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
                //db.execSQL("INSERT INTO User (name, balance) VALUES ('Andrey', 15)");
            }
        };

        userDatabase = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.NAME).addCallback(callback).allowMainThreadQueries().build();


    }

    public static App getInstance() {
        return instance;
    }

    public UserDatabase getDatabase() {
        return userDatabase;
    }
}
