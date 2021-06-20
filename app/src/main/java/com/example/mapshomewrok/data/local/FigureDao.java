package com.example.mapshomewrok.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mapshomewrok.data.models.Figure;

import java.util.List;

@Dao
public interface FigureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void putFigure(List<Figure> figure);

    @Query("SELECT * FROM Figure")
    List<Figure> getFigure();

    @Delete
    void deleteAll(List<Figure> figure);
}
