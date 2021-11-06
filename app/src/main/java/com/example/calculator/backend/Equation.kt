package com.example.calculator.backend

import android.util.Log
import org.mariuszgromada.math.mxparser.Expression

class Equation {
    private var equation: MutableList<String> = mutableListOf()
    private val operatorRegex = "[+\\-*/]".toRegex()

    fun append(value: String) {
        equation.add(value)
    }

    fun append(operator: Char) {
        equation.add(operator.toString())
    }

    fun dropLast() {
        if(equation.isNotEmpty())
            equation = equation.take(equation.size - 1).toMutableList()
    }

    fun endsWithOperator(): Boolean {
        // operator and preceding number
        return equation.size >= 2 && operatorRegex.matches(equation.last())
    }

    fun replaceLastOperator(operator: Char) {
        if(equation.isNotEmpty()) equation =
            (equation.take(equation.size - 1) + mutableListOf(operator.toString())).toMutableList()
    }

    fun clearEquation() {
        equation =  mutableListOf()
    }

    fun isValidDivision(number: Number): Boolean {
        return number.toString().toDouble() == 0.0 && equation.isNotEmpty() && equation.last() == "/"
    }

    private fun isNotEmpty(): Boolean {
        return equation.isNotEmpty()
    }

    fun evaluate(number: Number): Double {
        if(number.isEmpty() && isNotEmpty()) dropLast()
        else if(number.isEmpty()) append("0")
        else append(number.toString())

        val expression = Expression(toString())
        return expression.calculate()
    }

    override fun toString(): String {
        return equation.joinToString("")
    }
}