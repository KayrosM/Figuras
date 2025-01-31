package com.example.figuras.Model.Local.Databse



import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.figuras.Model.Local.Dao.FigureDao
import com.example.figuras.Model.Local.Entitys.FigureDetails
import com.example.figuras.Model.Local.Entitys.FigureList



@Database(entities = [FigureList::class, FigureDetails::class], version = 2, exportSchema = false)
abstract class FigureDatabase : RoomDatabase() {
    abstract fun getFigureDao(): FigureDao

    companion object {


        @Volatile
        private var INSTANCE: FigureDatabase? = null

        fun getDatabase(context: Context): FigureDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FigureDatabase::class.java,
                    "Figure_database"
                ).fallbackToDestructiveMigration() // ← Esto evita fallos por cambios de versión
                    .build()
                INSTANCE = instance
                instance

            }
        }
    }
}