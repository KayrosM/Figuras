package com.example.figuras.Model.Remote

import com.example.figuras.Model.Local.Entitys.FigureDetails
import com.example.figuras.Model.Local.Entitys.FigureList
import com.example.figuras.Model.Remote.FromInternet.DetailsFigure
import com.example.figuras.Model.Remote.FromInternet.ListFigure


fun fromInternetListFigure(figureList: List<ListFigure>): List<FigureList> {


    return figureList.map {
        FigureList(
            id = it.id,
            nombre = it.nombre,
            origen = it.origen,
            imagenLink = it.imagenLink,
            descripcion = it.descripcion,
            añoCreacion = it.añoCreacion,
            precio = it.precio,
            manual = it.manual
        )
    }
}




fun fromInternetDetailsFigure(detailsFigure: DetailsFigure): FigureDetails {

    return FigureDetails(
        id = detailsFigure.id,
        nombre = detailsFigure.nombre,
        origen = detailsFigure.origen,
        imagenLink = detailsFigure.imagenLink,
        descripcion = detailsFigure.descripcion,
        añoCreacion = detailsFigure.añoCreacion,
        precio = detailsFigure.precio,
        manual = detailsFigure.manual
    )
}