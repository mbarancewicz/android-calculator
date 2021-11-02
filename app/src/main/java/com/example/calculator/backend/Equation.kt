package com.example.calculator.backend

class Equation {
    private var equation: MutableList<String> = mutableListOf()
    private val operatorRegex = "[+\\-*/]".toRegex()

    fun append(value: String) {
        equation.add(value)
    }

    fun append(operator: Char) {
        equation.add(operator.toString())
    }

    fun append(number: Collection<Char>) {
        equation.add(number.joinToString(""))
    }

    fun dropLast() {
        if(equation.isNotEmpty())
            equation = equation.take(equation.size - 1).toMutableList()
    }

    fun endsWithOperator(): Boolean {
        return equation.isNotEmpty() && operatorRegex.matches(equation.last())
    }

    fun replaceLastOperator(operator: Char) {
        if(equation.isNotEmpty()) equation =
            (equation.take(equation.size - 1) + mutableListOf(operator.toString())).toMutableList()
    }

    fun clearEquation() {
        equation =  mutableListOf()
    }

    fun isValidDivision(number: Double): Boolean {
        return number == 0.0 && equation.isNotEmpty() && equation.last() == "/"
    }

    fun isNotEmpty(): Boolean {
        return equation.isNotEmpty()
    }

    fun evaluate() {
    // todo impl po impl number
    }

    override fun toString(): String {
        return equation.joinToString("")
    }
}