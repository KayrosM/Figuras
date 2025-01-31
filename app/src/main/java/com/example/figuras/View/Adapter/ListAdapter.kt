package com.example.figuras.View.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.figuras.Model.Local.Entitys.FigureList
import com.example.figuras.databinding.FigurelistBinding

class ListsAdapter : RecyclerView.Adapter<ListsAdapter.ViewHolder>() {

    private var listFigure = listOf<FigureList>()
    private val selectedFigure = MutableLiveData<FigureList>()

    // Método público para acceder al LiveData
    fun getSelectedFigure(): LiveData<FigureList> = selectedFigure

    inner class ViewHolder(private val binding: FigurelistBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        fun bind(list: FigureList) {
            Glide.with(binding.imageRv).load(list.imagenLink).centerCrop().into(binding.imageRv)
            binding.textNameRV.text = list.nombre
            binding.textPriceRV.text = "Precio: $${list.precio} "


            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            selectedFigure.value = listFigure[bindingAdapterPosition]
            Log.d("ONCLICK", bindingAdapterPosition.toString())
        }
    }

    fun update(list: List<FigureList>) {
        listFigure = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FigurelistBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listFigure.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listFigure[position])
    }
}
