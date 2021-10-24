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
        if(isValidNumber(number)) {
            numbers.add(number)
            updateDisplay(v)
        }
    }

    fun insertOperator(v: View) {
        val operator = v.tag.toString().single()
        val operatorRegex = "[+\\-*/]".toRegex()

        if(numbers.isNotEmpty() || !operatorRegex.matches(equation.last())) {
            equation.add(numbers.joinToString(""))
            equation.add(operator.toString())
            updateEquationDisplay(v)

            numbers = mutableListOf()
            updateDisplay(v)
        }
    }

    fun evaluate(view: View) {
        val number = view.rootView.findViewById<TextView>(R.id.display).text.toString()
        equation.add(number)
        updateEquationDisplay(view)

        val equationString = equation.joinToString("")

        val e = Expression(equationString)
        val result = e.calculate()
        Log.d("equation", "" + equationString)
        Log.d("result", "" + result)

        // zostawia slad wyniku w rownaniu
        numbers = (result.toString().map { c -> c }).toMutableList()
        updateDisplay(view)
    }


    fun clear(v: View) {
        numbers = mutableListOf()
        updateDisplay(v)
    }

    fun clearAll(v: View) {
        clear(v)
        equation = mutableListOf()
        updateEquationDisplay(v)
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
        val notStartWithZero = if(numbers.isEmpty()) c != '.' else true
        val nonZeroAtBeginning = numbers.isNotEmpty() || c != '0'
        val notTooLong = numbers.size < 15

        return validPoint && notStartWithZero && nonZeroAtBeginning && notTooLong
    }
}