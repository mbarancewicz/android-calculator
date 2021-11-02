package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.mariuszgromada.math.mxparser.Expression


class StandardCalc : AppCompatActivity() {
    private var numbers = mutableListOf<Char>()
    private var equation = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standard)
    }

    fun insertNumber(v: View) {
        val number = v.tag.toString().single()
        fun replaceZero() = numbers.size == 1 && numbers[0] == '0' && number in '1' .. '9'

        if(isValidNumber(number)) {
            numbers.add(number)
            updateDisplay(v)
        } else if(replaceZero()) {
            numbers = mutableListOf(number)
            updateDisplay(v)
        }
    }

    fun insertOperator(v: View) {
        val operator = v.tag.toString().single()
        val operatorRegex = "[+\\-*/]".toRegex()


        if(numbers.isNotEmpty() || !operatorRegex.matches(equation.last())) {
            if(numbers[numbers.size - 1] == '.')
                numbers = numbers.take(numbers.size - 1).toMutableList()
            if(numbers[0] == '-')
                numbers = (mutableListOf('(') + numbers + mutableListOf(')')).toMutableList()

            equation.add(numbers.joinToString(""))
            equation.add(operator.toString())
            updateEquationDisplay(v)

            numbers = mutableListOf()
            updateDisplay(v)
        } else if(numbers.isEmpty() || operatorRegex.matches(equation.last())) {
            equation = (equation.take(equation.size - 1) + mutableListOf(operator.toString())).toMutableList()
            updateEquationDisplay(v)
        }
    }

    fun evaluate(view: View) {
        var number = view.rootView.findViewById<TextView>(R.id.display).text.toString()
        if(number.toDouble() == 0.0 && equation.isNotEmpty() && equation.last() == "/") {
            Log.e("math", "division by 0")
            clear(view)
            updateDisplay(view)
        } else {
            if(number.endsWith(".")) number = number.take(number.length - 1)
            if(number.startsWith("-")) number = "($number)"

            if(number.isEmpty() && equation.isNotEmpty())
                equation = equation.take(equation.size - 1).toMutableList()
            else if(number.isEmpty()) equation.add("0")
            else equation.add(number)

            updateEquationDisplay(view)

            val equationString = equation.joinToString("")

            val e = Expression(equationString)
            val result = e.calculate()
            Log.d("equation", "" + equationString)
            Log.d("result", "" + result)

            numbers =
                if (result.toInt().toDouble() == result)
                    (result.toInt().toString().map { c -> c }).toMutableList()
                else (result.toString().map { c -> c }).toMutableList()
            updateDisplay(view)

            clearEquation(view)
        }
    }


    fun clear(v: View) {
        numbers = mutableListOf()
        updateDisplay(v)
    }

    fun clearEquation(v: View) {
        equation = mutableListOf()
        updateEquationDisplay(v)
    }

    fun clearAll(v: View) {
        clear(v)
        clearEquation(v)
    }

    private fun updateDisplay(v: View) {
        val display = v.rootView.findViewById<TextView>(R.id.display)
        display.text = numbers.joinToString("")
    }

    private fun updateEquationDisplay(v: View) {
        val equationDisplay = v.rootView.findViewById<TextView>(R.id.equationDisplay)
        equationDisplay.text = equation.joinToString("")

        val scrollView = v.rootView.findViewById<ScrollView>(R.id.scrollable)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }

    private fun isValidNumber(c: Char): Boolean {
        val validPoint = c != '.' || !numbers.contains('.')
        val nonPointAtBeginning = numbers.isNotEmpty() || c != '.'
        val properDecimalNumber = if(numbers.size == 1 && numbers[0] == '0') c == '.' else true
        val notTooLong = numbers.size < 15

        return validPoint && nonPointAtBeginning && properDecimalNumber && notTooLong
    }

    fun toggleSign(view: View) {
        if(numbers.isNotEmpty() && !(numbers.size == 1 && numbers[0] == '0')
            && !(numbers.size == 2 && numbers[0] == '0' && numbers[1] == '.')) {
            numbers = if(numbers[0] != '-') (mutableListOf('-') + numbers).toMutableList()
            else numbers.drop(1).toMutableList()
        }
        updateDisplay(view)
    }

    fun clearLast(view: View) {
        if(numbers.isNotEmpty()) {
            numbers = numbers.take(numbers.size - 1).toMutableList()
            if(numbers.size == 1 && numbers[0] == '-') numbers = mutableListOf()
        }
        updateDisplay(view)
    }
}

// TODO
//  bledy walidacji w toascie
//  poprawa czcionki i rozmiaru