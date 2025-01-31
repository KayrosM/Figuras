package com.example.figuras.ViewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.figuras.Model.FigureRepository
import com.example.figuras.Model.Local.Databse.FigureDatabase
import com.example.figuras.Model.Local.Entitys.FigureDetails
import com.example.figuras.Model.Local.Entitys.FigureList
import kotlinx.coroutines.launch

class FigureViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FigureRepository
    private var idSelected: Int = 0

    init {
        val bd = FigureDatabase.getDatabase(application.applicationContext) //usa applicacion contex
        val Dao = bd.getFigureDao()
        repository = FigureRepository(Dao)
        viewModelScope.launch {
            repository.fetchList()
        }


    }

    fun getFigureList(): LiveData<List<FigureList>> = repository.figureListLiveData



    // es cuando selecciono una
    fun getFigureDetailsByIdFromInternet(id: Int) = viewModelScope.launch {
        idSelected = id
        repository.fetchFigureDetails(id)

    }


    // obtener el detalle de una flor
    fun getFigureDetail(): LiveData<FigureDetails> = repository.getFigureDetailsById(idSelected)
}