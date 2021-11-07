package com.example.calculator.calc

import android.view.View
import com.example.calculator.R
import com.example.calculator.backend.Equation
import com.example.calculator.backend.Number

class ExtendedCalcActivity : CalcActivity() {
    override fun setLayout(): Int = R.layout.activity_extended_calc

    override fun insertOperator(v: View) {
        val operator = v.tag.toString()
        val extendedOperators = "sin|cos|tan|ln|sqrt|log".toRegex()
        if(number.isNotEmpty() && operator == "%") {
            val localEquation = Equation()
            number.formatNumberForEquation()
            localEquation.append(number.toString())
            localEquation.append("/")
            val result = localEquation.evaluate(Number(100.0))

            number = Number(result)
            super.updateDisplay(v)
        } else if(number.isNotEmpty() && extendedOperators.matches(operator) && !equation.endsWithExtendedOperator()) {
            number.formatNumberForEquation()

            equation.append(operator)
            equation.append("(")
            equation.append(number.toString())
            equation.append(")")

            number.clearNumber()
            super.updateDisplay(v)
        } else if(number.isEmpty() && !extendedOperators.matches(operator) && equation.endsWithExtendedOperator()) {
            equation.append(operator)
        } else if(!equation.endsWithExtendedOperator() && !extendedOperators.matches(operator) && operator != "%")
            super.insertOperator(v)
        else toastError()
        updateEquationDisplay(v)
    }
}
