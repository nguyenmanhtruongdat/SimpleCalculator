package com.example.simplecalculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.simplecalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var expression: Expression
    private var lastNumeric = false
    private var stateError = false
    private var lastDot = false
    private var isSinClicked= false
    private var isCosClicked= false
    private var isTanClicked= false
    private var isCotClicked= false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun onDigitClick(view: View) {
        if (stateError) {
            binding.workingsTV.text = (view as Button).text
            stateError = false
        } else {
            val decimalButton = (view as Button).text
            if (decimalButton == ".") {
                if (!binding.workingsTV.text.contains(".")) {
                    binding.workingsTV.append(decimalButton)
                }
            } else {
                binding.workingsTV.append(decimalButton)
            }
        }
        lastNumeric = true
    }


    fun onAllClearClick(view: View) {
        binding.workingsTV.text = ""
        binding.resultsTV.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false

    }

    fun onEqualClick(view: View) {
        onEqual()
        binding.workingsTV.text = ""

    }

    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric) {
            binding.workingsTV.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()

        }
    }

//    fun onOperatorClick(view: View) {
//        if (!stateError && lastNumeric) {
//            val currentOperator = (view as Button).text.toString()
//            val workings = binding.workingsTV.text.toString()
//
//            if (workings.isNotEmpty()) {
//                val lastChar = workings[workings.length - 1]
//                if (lastChar == '+' || lastChar == '-' || lastChar == '*' || lastChar == '/') {
//                    // replace the last operator with the new one
//                    val newWorkings = workings.substring(0, workings.length - 1) + currentOperator
//                    binding.workingsTV.text = newWorkings
//                } else {
//                    // append the new operator
//                    binding.workingsTV.append(currentOperator)
//                }
//            } else {
//                // the first character cannot be an operator
//                binding.workingsTV.append("0$currentOperator")
//            }
//
//            lastDot = false
//            lastNumeric = false
//        }
//    }



    fun backSpaceClick(view: View) {
        binding.workingsTV.text = binding.workingsTV.text.toString().dropLast(1)
        try {
            var lastNum = binding.workingsTV.text.toString().last()
//            if (lastNum.isDigit()){
//                onEqual()
//            }
        } catch (e: java.lang.Exception) {
            binding.resultsTV.text = ""

        }
    }

//    private fun onEqual() {
//        if (lastNumeric && !stateError) {
//            val data = binding.workingsTV.text.toString()
//            expression = ExpressionBuilder(data).build()
//            try {
//                val result = expression.evaluate()
//                binding.resultsTV.text = result.toString()
//
//            } catch (ex: java.lang.ArithmeticException) {
//                Log.e("evaluate exception", ex.toString())
//                binding.resultsTV.text = "Error"
//                stateError = true
//                lastNumeric = false
//            }
//        }
//    }

    private fun onEqual() {

        if (lastNumeric && !stateError) {
            var data = binding.workingsTV.text.toString()

            if (isSinClicked) {
                // If sin button was clicked, wrap the expression in sin()
                data = "sin($data)"
                isSinClicked = false
                data = data.replace("sin(", "radians(sin(")
            }
            if (isCosClicked) {
                // If sin button was clicked, wrap the expression in sin()
                data = "cos($data)"
                isSinClicked = false
                data = data.replace("cos(", "radians(cos(")
            }
            if (isTanClicked) {
                // If sin button was clicked, wrap the expression in sin()
                data = "tan($data)"
                isSinClicked = false
                data = data.replace("tan(", "radians(tan(")
            }
            if (isCotClicked) {
                // If sin button was clicked, wrap the expression in sin()
                data = "cot($data)"
                isSinClicked = false
                data = data.replace("cot(", "radians(cot(")
            }

            val expression = ExpressionBuilder(data).build()

            try {
                val result = expression.evaluate()
                binding.resultsTV.text = result.toString()
            } catch (ex: ArithmeticException) {
                Log.e("evaluate exception", ex.toString())
                binding.resultsTV.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }




}









