package com.example.figuras.Model.Remote.FromInternet



data class ListFigure(
    val id: Int,
    val nombre: String,
    val origen: String,
    val imagenLink: String,
    val descripcion: String,
    val añoCreacion: Int,
    val precio: Int,
    val manual: Boolean
)
