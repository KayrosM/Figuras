package com.example.figuras.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.figuras.R
import com.example.figuras.ViewModel.FigureViewModel
import com.example.figuras.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private val viewModel : FigureViewModel by activityViewModels()

    private var flowerId : Int = 0
    private var flowerName : String =""






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // recibo los valores del primer fragmento
        arguments?.let { bundle ->

            flowerId = bundle.getInt("FigureId",-1)
            flowerName = bundle.getString("FigureName","")?: ""
            val flowerPrice = bundle.getInt("FigurePrice", 0)
            Log.d("bundle", "Bundle recibido: flowerid=$flowerId, flowerNombre=$flowerName")

            binding.textPriceD.text = "Precio:$ $flowerPrice "

        }

        // tengo que pasarle el id a la función del VIewModel

        flowerId.let { id ->
            viewModel.getFigureDetailsByIdFromInternet(id)
        }


        // observo los cambios

        viewModel.getFigureDetail().observe(viewLifecycleOwner) { figureDetails ->


            if (figureDetails != null) {

                Glide.with(binding.imageD).load(figureDetails.imagenLink).into(binding.imageD)
                binding.textNameD.text =
                    binding.root.context.getString(R.string.NombreDetalles, figureDetails.nombre)



                binding.textDescripcionD.text =
                    binding.root.context.getString(
                        R.string.DescripcionDetalles,
                        figureDetails.descripcion
                    )

                binding.fab.setOnClickListener {
                    enviarCorreo(flowerName)
                    Log.d("Button", "Contactar")

                }

            }


        }
    }

    fun enviarCorreo(nombre: String) {
        Log.d("fun", "función enviar mensaje WhatsApp")

        val phoneNumber = "+5697683223" // Número del destinatario
        val message = "Hola,\n\n" +
                "Vi el producto $nombre y me gustaría recibir más información.\n" +
                "Quedo atento/a."

        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://wa.me/$phoneNumber?text=${Uri.encode(message)}")
        }

        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No se pudo abrir WhatsApp", Toast.LENGTH_SHORT).show()
        }
    }






    override fun onDestroyView() {
        super.onDestroyView()
        //  _binding = null
    }
}