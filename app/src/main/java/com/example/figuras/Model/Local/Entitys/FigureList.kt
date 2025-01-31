package com.example.figuras.Model.Local.Entitys


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "List_Figure")
data class FigureList(
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