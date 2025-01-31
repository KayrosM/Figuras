package com.example.figuras.Model.Local.Entitys




import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Details_Figure")
data class FigureDetails(
    @PrimaryKey
    val id: Int,
    val nombre: String,
    val origen: String,
    val imagenLink: String,
    val descripcion: String,
    val añoCreacion: Int,
    val precio: Int,
    val manual: Boolean
)