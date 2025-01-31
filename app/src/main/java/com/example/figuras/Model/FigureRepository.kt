package com.example.figuras.Model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.figuras.Model.Local.Dao.FigureDao
import com.example.figuras.Model.Local.Entitys.FigureDetails
import com.example.figuras.Model.Local.Entitys.FigureList
import com.example.figuras.Model.Remote.fromInternetDetailsFigure
import com.example.figuras.Model.Remote.fromInternetListFigure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FigureRepository(private val figureDao: FigureDao) {

    private val networkService = RetrofitFigure.retrofitInstance()

    val figureListLiveData: LiveData<List<FigureList>> = figureDao.getAllFigure()

    suspend fun fetchList() {
        val response = runCatching { networkService.fetchFigureList() }

        response.onSuccess {
            Log.d("Retrofit", "URL llamada: ${networkService.fetchFigureList().raw().request.url}")

            if (it.isSuccessful) {
                it.body()?.let { list ->
                    Log.d("Retrofit", "Datos recibidos: $list")
                    figureDao.insertAllFigure(fromInternetListFigure(list))
                }
            } else {
                Log.e("Retrofit", "Error ${it.code()} - ${it.errorBody()?.string()}")
            }
        }.onFailure {
            Log.e("Retrofit", "Error de conexión: ${it.message}")
        }
    }


    suspend fun fetchFigureDetails(id: Int): FigureDetails? {
        val response = runCatching { networkService.fetchFigureDetail(id) }

        return response.getOrNull()?.body()?.let { details ->
            val figureDetails = fromInternetDetailsFigure(details)
            withContext(Dispatchers.IO) {
                figureDao.insertFigureDetail(figureDetails)
            }
            figureDetails
        }
    }

    fun getFigureDetailsById(id: Int): LiveData<FigureDetails> {
        return figureDao.getFigureDetailById(id)
    }
}

