package com.example.calculator.backend

import org.mariuszgromada.math.mxparser.Expression
import java.util.regex.Matcher
import java.util.regex.Pattern

class Equation {
    private var equation: MutableList<String> = mutableListOf()
    private val operatorRegex = "[+\\-*/]".toRegex()
    private val extendedOperators = "(.+?)?(sin|cos|tan|ln|sqrt|log)\\((.+?)\\)$".toRegex()

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

    fun isInvalidDivision(number: Number): Boolean {
        return number.toString().toDouble() == 0.0 && equation.isNotEmpty() && equation.last() == "/"
    }

    private fun isNotEmpty(): Boolean {
        return equation.isNotEmpty()
    }

    fun evaluate(number: Number): Double {
        if(number.isEmpty() && isNotEmpty() && !extendedOperators.matches(toString())) dropLast()
        else if(number.isEmpty() && !extendedOperators.matches(toString())) append("0")
        else append(number.toString())

        val expression = Expression(formatEquation())
        return expression.calculate()
    }

    override fun toString(): String {
        return equation.joinToString("")
    }

    fun endsWithExtendedOperator(): Boolean {
        return toString().length >= 6 && extendedOperators.matches(toString())
    }

    private fun formatEquation(): String {
        val p: Pattern = Pattern.compile("log\\((.+?)\\)")
        val m: Matcher = p.matcher(toString())
        return if (m.find()) {
            m.replaceAll("log(10,$1)")
        } else toString()
    }
}