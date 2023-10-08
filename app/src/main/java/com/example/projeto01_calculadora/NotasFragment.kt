package com.example.projeto01_calculadora

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NotasFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notas, container, false)
    }


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val etNota1 = view.findViewById<EditText>(R.id.nota1)
        val etNota2 = view.findViewById<EditText>(R.id.nota2)
        val etNota3 = view.findViewById<EditText>(R.id.nota3)
        val btnCalcular = view.findViewById<Button>(R.id.btnCalcular)
        val tvResultado = view.findViewById<TextView>(R.id.resultadoNotas)

        btnCalcular.setOnClickListener {
            val nota1Text = etNota1.text.toString()
            val nota2Text = etNota2.text.toString()
            val nota3Text = etNota3.text.toString()

            if (nota1Text.isEmpty()) {
                FragmentUtils.hideKeyboard(view);

                Toast.makeText(
                    requireContext(),
                    "Por favor, insira a nota da 1ª unidade",
                    Toast.LENGTH_SHORT
                ).show();
            }

            val nota1 = nota1Text.toFloatOrNull()
            val nota2 = nota2Text.toFloatOrNull()
            val nota3 = nota3Text.toFloatOrNull()

            if (nota1 != null) {
                if (nota2 != null && nota3 != null) {
                    val media = (nota1 + nota2 + nota3) / 3

                    if (media >= 7.0) {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Aprovado com média: $media"
                    } else if (media >= 5.0) {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Aprovado por nota com média: $media"
                    } else {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Reprovado com média: $media"
                    }
                } else if (nota2 != null) {
                    val mediaNecessaria = (21 - (nota1 + nota2)) / 3

                    if (mediaNecessaria <= 10) {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Com $mediaNecessaria na 3ª você será aprovado por média"
                    } else {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Você não pode ser aprovado por média"
                    }
                } else {
                    val notaNecessaria = (10 - nota1).coerceAtLeast(0f)

                    if (notaNecessaria <= 10) {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Com $notaNecessaria na 2ª e na 3ª você será aprovado por nota"
                    } else {
                        FragmentUtils.hideKeyboard(view);
                        tvResultado.text = "Você não pode ser aprovado por nota"
                    }
                }
            }
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotasFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}