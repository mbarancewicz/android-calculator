package com.example.calculator.backend

class Number {
    private var number = mutableListOf<Char>()

    constructor()

    constructor(number: Double) {
        parse(number)
    }

    fun append(digit: Char) {
        if(isValidDigit(digit)) number.add(digit)
        else if(isReplaceableZero(digit)) replaceLastDigit(digit)
    }

    fun clearNumber() {
        number = mutableListOf()
    }

    fun isEmpty() = number.isEmpty()

    fun isNotEmpty() = number.isNotEmpty()

    fun formatNumberForEquation() {
        if(number.isNotEmpty() && number[number.size - 1] == '.')
            removeLastDigit()
        if(number.isNotEmpty() && number[0] == '-')
            number = (mutableListOf('(') + number + mutableListOf(')')).toMutableList()
    }

    fun toggleSign() {
        if(number.isNotEmpty() && !(number.size == 1 && number[0] == '0')
            && !(number.size == 2 && number[0] == '0' && number[1] == '.')) {
            number = if(number[0] != '-') (mutableListOf('-') + number).toMutableList()
            else number.drop(1).toMutableList()
        }
    }

    fun removeLastDigit() {
        if(number.isNotEmpty()) {
            number = number.take(number.size - 1).toMutableList()
            if(number.size == 1 && number[0] == '-') clearNumber()
        }
    }

    override fun toString(): String {
        return number.joinToString("")
    }

    private fun parse(number: Double) {
        this.number = if (number.toInt().toDouble() == number)
            (number.toInt().toString().map { c -> c }).toMutableList()
        else (number.toString().map { c -> c }).toMutableList()
    }

    private fun replaceLastDigit(digit: Char) {
        if(number.isNotEmpty()) number =
            (number.take(number.size - 1) + mutableListOf(digit)).toMutableList()
    }

    private fun isReplaceableZero(digit: Char) =
        number.size == 1 && number[0] == '0' && digit in '1' .. '9'

    private fun isValidDigit(c: Char): Boolean {
        val validPoint = c != '.' || !number.contains('.')
        val nonPointAtBeginning = number.isNotEmpty() || c != '.'
        val properDecimalNumber = if(number.size == 1 && number[0] == '0') c == '.' else true
        val notTooLong = number.size < 15

        return validPoint && nonPointAtBeginning && properDecimalNumber && notTooLong
    }
}