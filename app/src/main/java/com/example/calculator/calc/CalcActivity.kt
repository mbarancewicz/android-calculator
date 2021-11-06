package com.example.calculator.calc

import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.backend.Equation
import com.example.calculator.backend.Number

abstract class CalcActivity : AppCompatActivity() {
    abstract fun setLayout(): Int

    private var number = Number()
    private var equation = Equation()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setLayout())
    }

    fun insertNumber(v: View) {
        val digit = v.tag.toString().single()

        number.append(digit)
        updateDisplay(v)
    }

    fun insertOperator(v: View) {
        val operator = v.tag.toString().single()

        if(number.isNotEmpty()) {
            number.formatNumberForEquation()
            equation.append(number.toString())

            equation.append(operator)
            number.clearNumber()

            updateDisplay(v)
        } else if(number.isEmpty() && equation.endsWithOperator()) {
            equation.replaceLastOperator(operator)
        }
        updateEquationDisplay(v)
    }

    fun evaluate(view: View) {
        if(number.isNotEmpty() && equation.isValidDivision(number)) {
            val text = "Division by 0! Insert another value"
            val duration = Toast.LENGTH_LONG
            Toast.makeText(applicationContext, text, duration).show()

            clearNumber(view)
            updateDisplay(view)
        } else {
            number.formatNumberForEquation()

            val result = equation.evaluate(number)
            number = Number(result)

            clearEquation(view)
            updateDisplay(view)
        }
    }

    fun toggleSign(view: View) {
        number.toggleSign()
        updateDisplay(view)
    }

    fun clearNumber(v: View) {
        if(number.isEmpty()) clearEquation(v)
        else number.clearNumber()
        updateDisplay(v)
    }

    fun clearAll(v: View) {
        clearNumber(v)
        clearEquation(v)
    }

    fun clearLast(view: View) {
        number.removeLastDigit()
        updateDisplay(view)
    }

    private fun clearEquation(v: View) {
        equation.clearEquation()
        updateEquationDisplay(v)
    }


    private fun updateDisplay(v: View) {
        val display = v.rootView.findViewById<TextView>(R.id.display)
        display.text = number.toString()
    }

    private fun updateEquationDisplay(v: View) {
        val equationDisplay = v.rootView.findViewById<TextView>(R.id.equationDisplay)
        equationDisplay.text = equation.toString()

        val scrollView = v.rootView.findViewById<ScrollView>(R.id.scrollable)
        scrollView.post {
            scrollView.fullScroll(View.FOCUS_DOWN)
        }
    }
}