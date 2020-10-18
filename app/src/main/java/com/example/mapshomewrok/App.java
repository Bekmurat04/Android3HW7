package com.example.mapshomewrok;

import android.app.Application;

import androidx.room.Room;

import com.example.mapshomewrok.data.local.FigureDatabase;

public class App extends Application {
    public static FigureDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
       database = Room.databaseBuilder(this,FigureDatabase.class,"FigureDatabase")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

}
