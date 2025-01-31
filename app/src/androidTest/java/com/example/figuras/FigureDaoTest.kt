package com.example.figuras

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.figuras.Model.Local.Dao.FigureDao
import com.example.figuras.Model.Local.Databse.FigureDatabase
import com.example.figuras.Model.Local.Entitys.FigureList
import getOrAwaitValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.test.runTest

@RunWith(AndroidJUnit4::class)
class FigureDaoTest {

    private lateinit var figureDao: FigureDao
    private lateinit var figureDB: FigureDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        figureDB = Room.inMemoryDatabaseBuilder(context, FigureDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        figureDao = figureDB.getFigureDao()
    }

    @After
    fun tearDown() {
        figureDB.close()
    }

    @Test
    fun testInsertAndRetrieveFigures() = runTest {
        val figures = listOf(
            FigureList(1, "Figura1", "Origen1", "imagen1", "descripcion1", 2000, 150, true),
            FigureList(2, "Figura2", "Origen2", "imagen2", "descripcion2", 1999, 300, false)
        )
        figureDao.insertAllFigure(figures)

        val retrievedFigures = figureDao.getAllFigure().getOrAwaitValue()

        assertThat(retrievedFigures.size, equalTo(2))
        assertThat(retrievedFigures[0].nombre, equalTo("Figura1"))
        assertThat(retrievedFigures[1].nombre, equalTo("Figura2"))
    }
}

