package com.example.mapshomewrok.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.mapshomewrok.data.models.Figure;

@Database(entities = {Figure.class},version = 1)
public abstract class FigureDatabase extends RoomDatabase {
    public abstract FigureDao figureDao();
}
