package com.example.calculator.calc

import android.util.Log
import android.view.View
import com.example.calculator.R

class ExtendedCalcActivity : CalcActivity() {
    override fun setLayout(): Int = R.layout.activity_extended_calc

    override fun insertOperator(v: View) {
        val operator = v.tag.toString()
        val extendedOperators = "sin|cos|tan|ln|sqrt".toRegex()
        if(number.isNotEmpty() && extendedOperators.matches(operator) && !equation.endsWithExtendedOperator()) {
            number.formatNumberForEquation()

            equation.append(operator)
            equation.append("(")
            equation.append(number.toString())
            equation.append(")")

            number.clearNumber()
            super.updateDisplay(v)
        } else if(number.isEmpty() && !extendedOperators.matches(operator) && equation.endsWithExtendedOperator()) {
            equation.append(operator)
        } else if(!equation.endsWithExtendedOperator() && !extendedOperators.matches(operator))
            super.insertOperator(v)
        else toastError()
        updateEquationDisplay(v)
    }
}

// todo implement extended operators
//  implement operator taking 2 values (log, power)
