package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import android.util.Log;


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
        val context = Context.enter() //
        context.optimizationLevel = -1 // this is required[2]

        val number = view.rootView.findViewById<TextView>(R.id.display).text.toString()
        val equationString = view.rootView.findViewById<TextView>(R.id.equationDisplay).text.toString() + number
        Log.d("equation", "" + equationString)

        val scope: Scriptable = context.initStandardObjects()
        val result = context.evaluateString(scope, equationString, "<cmd>", 1, null)
        Log.d("your-tag-here", "" + result)

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