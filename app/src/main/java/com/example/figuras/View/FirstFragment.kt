package com.example.figuras.View

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.figuras.View.Adapter.ListsAdapter
import com.example.figuras.R
import com.example.figuras.ViewModel.FigureViewModel
import com.example.figuras.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: FigureViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ListsAdapter()
        binding.RvList.adapter = adapter
        binding.RvList.layoutManager = GridLayoutManager(context, 2)

        viewModel.getFigureList().observe(viewLifecycleOwner) { list ->
            list?.let {
                Log.d("LISTADO", it.toString())
                adapter.update(it)
            }
        }

        adapter.getSelectedFigure().observe(viewLifecycleOwner, Observer { selectedFigure ->
            selectedFigure?.let {
                Log.d("FigureChosen", it.toString())
                viewModel.getFigureDetailsByIdFromInternet(it.id)

                val bundle = Bundle().apply {
                    putInt("FigureId", it.id)
                    putString("FigureName", it.nombre)
                    putInt("FigurePrice", it.precio)
                }

                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            }
        })
    }
}

