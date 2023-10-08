package com.example.projeto01_calculadora

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CalculadoraFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_calculadora, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //preparar tela
        setUp(view);

        //eventos com relacao aos numeros
        eventosNumeros(view);

        //eventos com relacao aos operadores
        eventosOperadores(view);

        //eventos com relacao as operacoes matematicas
        realizarCalculo(view)

        //checar para exibir no resultado
        onChangedValue(view);


    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setUp(view: View) {

        var expressao = view?.findViewById<TextInputEditText>(R.id.expressao);

        expressao?.post() {
            expressao.requestFocus();
        }
        expressao?.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_UP) {
                expressao.requestFocus();
                FragmentUtils.hideKeyboard(view);
                return@setOnTouchListener true;
            }

            return@setOnTouchListener false;

        }


    }

    private fun onChangedValue(view: View) {
        var expressao = view?.findViewById<TextInputEditText>(R.id.expressao);
        expressao?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (expressao.text.toString().contains("%") || expressao.text.toString()
                        .contains("+") || expressao.text.toString()
                        .contains("-") || expressao.text.toString()
                        .contains("/") || expressao.text.toString().contains("x")
                ) {
                    realizarCalculo(view)
                }

            }
        })
    }


    private fun eventosNumeros(view: View) {

        var numeroZero = view.findViewById<TextView>(R.id.numeroZero);
        var numeroUm = view.findViewById<TextView>(R.id.numeroUm);
        var numeroDois = view.findViewById<TextView>(R.id.numeroDois);
        var numeroTres = view.findViewById<TextView>(R.id.numeroTres);
        var numeroQuatro = view.findViewById<TextView>(R.id.numeroQuatro);
        var numeroCinco = view.findViewById<TextView>(R.id.numeroCinco);
        var numeroSeis = view.findViewById<TextView>(R.id.numeroSeis);
        var numeroSete = view.findViewById<TextView>(R.id.numeroSete);
        var numeroOito = view.findViewById<TextView>(R.id.numeroOito);
        var numeroNove = view.findViewById<TextView>(R.id.numeroNove);
        var apagarTudo = view.findViewById<TextView>(R.id.apagarTudo);
        var apagar = view.findViewById<ImageView>(R.id.apagar);
        var ponto = view.findViewById<TextView>(R.id.ponto);
        var virgula = view.findViewById<TextView>(R.id.virgula);


        numeroZero.setOnClickListener {
            acrescentarExpressao(view, "0");
        }
        numeroUm.setOnClickListener {
            acrescentarExpressao(view, "1");
        }
        numeroDois.setOnClickListener {
            acrescentarExpressao(view, "2");
        }
        numeroTres.setOnClickListener {
            acrescentarExpressao(view, "3");
        }
        numeroQuatro.setOnClickListener {
            acrescentarExpressao(view, "4");
        }
        numeroCinco.setOnClickListener {
            acrescentarExpressao(view, "5");
        }
        numeroSeis.setOnClickListener {
            acrescentarExpressao(view, "6");
        }
        numeroSete.setOnClickListener {
            acrescentarExpressao(view, "7");
        }
        numeroOito.setOnClickListener {
            acrescentarExpressao(view, "8");
        }
        numeroNove.setOnClickListener {
            acrescentarExpressao(view, "9");
        }
        ponto.setOnClickListener {
            acrescentarExpressao(view, ".");
        }
        virgula.setOnClickListener {
            acrescentarExpressao(view, ",");
        }
        apagarTudo.setOnClickListener {
            apagarTudo(view);
        }
        apagar.setOnClickListener {
            apagar(view);
        }

    }

    private fun acrescentarExpressao(view: View, string: String, limparResultado: Boolean = true) {
        var expressao = view.findViewById<TextInputEditText>(R.id.expressao);
        var resultado = view.findViewById<TextView>(R.id.resultado);
        if (resultado.text.isNotEmpty()) {
            expressao.setText("");
        }
        if (limparResultado) {
            resultado.text = "";
            expressao.append(string);
        } else {
            expressao.append(resultado.text);
            expressao.append(string);
            resultado.text = "";
        }
    }

    private fun apagarTudo(view: View) {
        val expressao = view.findViewById<TextInputEditText>(R.id.expressao);
        expressao.setText("");
        expressao.requestFocus();
        view.findViewById<TextView>(R.id.resultado).text = "";
    }

    private fun apagar(view: View) {
        var expressao = view.findViewById<TextInputEditText>(R.id.expressao);
        var resultado = view.findViewById<TextView>(R.id.resultado);
        resultado.text = "";
        if (expressao.text?.isNotBlank() == true) {
            expressao.setText(
                expressao.text.toString().substring(0, expressao.text.toString().length - 1)
            );
            expressao.setSelection(expressao.text.toString().length);
        }
    }

    private fun eventosOperadores(view: View) {
        val somar = view.findViewById<TextView>(R.id.somar);
        val subtrair = view.findViewById<TextView>(R.id.subtrair);
        val multiplicar = view.findViewById<TextView>(R.id.multiplicar);
        val dividir = view.findViewById<TextView>(R.id.dividir);
        val igual = view.findViewById<TextView>(R.id.igual);


        somar.setOnClickListener {
            acrescentarExpressao(view, "+", false);
        }
        subtrair.setOnClickListener {
            acrescentarExpressao(view, "-", false);
        }
        multiplicar.setOnClickListener {
            acrescentarExpressao(view, "x", false);
        }
        dividir.setOnClickListener {
            acrescentarExpressao(view, "/", false);
        }

        igual.setOnClickListener {
            realizarCalculo(view, true)
        }
    }


    private fun realizarCalculo(view: View, isFinal: Boolean = false) {
        val expressao = view?.findViewById<TextView>(R.id.expressao);
        val resultado = view?.findViewById<TextView>(R.id.resultado);
        lateinit var calculo: Expression;
        var calculoEvaluate: Double = 0.0;
        var calculoResultado: Long;
        try {
            if (expressao?.text.toString().contains("%")) {
                val percentagem = expressao?.text.toString().replace("%", "/100");
                calculo =
                    ExpressionBuilder(percentagem).build();
                calculoEvaluate = calculo.evaluate();
                calculoResultado = calculoEvaluate.toLong();
            } else if (expressao?.text.toString().contains("x")) {
                calculo =
                    ExpressionBuilder(expressao?.text.toString().replace("x", "*")).build();
                calculoEvaluate = calculo.evaluate();
                calculoResultado = calculoEvaluate.toLong();

            } else {
                calculo =
                    ExpressionBuilder(expressao?.text.toString()).build();
                calculoEvaluate = calculo.evaluate();
                calculoResultado = calculoEvaluate.toLong();

            }
            if (isFinal) {
                acrescentarExpressao(view, resultado?.text.toString())
            } else {
                if (calculoEvaluate == calculoResultado.toDouble()) {
                    resultado?.text = calculoResultado.toString();
                } else {
                    val a = calculoEvaluate.toString();
                    resultado?.text = a;

                }
            }

        } catch (ex: ArithmeticException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.erroDivisaoZero),
                Toast.LENGTH_SHORT
            ).show();
        } catch (e: Exception) {
            println(e);
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculadoraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}