package com.example.figuras.Model.Local.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.figuras.Model.Local.Entitys.FigureDetails
import com.example.figuras.Model.Local.Entitys.FigureList


@Dao
interface FigureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFigure(figureList: List<FigureList>) // Debería recibir una lista

    @Query("SELECT * FROM List_Figure ORDER BY id ASC")
    fun getAllFigure(): LiveData<List<FigureList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFigureDetail(figureDetails: FigureDetails) // Error corregido

    @Query("SELECT * FROM Details_Figure WHERE id = :id")
    fun getFigureDetailById(id: Int): LiveData<FigureDetails>


}

