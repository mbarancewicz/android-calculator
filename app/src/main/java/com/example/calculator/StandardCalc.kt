package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StandardCalc : AppCompatActivity() {
    private var numbers = mutableListOf<Char>()

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

    fun evaluate(view: View) {}

    fun clear(v: View) {
        numbers = mutableListOf()
        updateDisplay(v)
    }

    fun clearAll(v: View) {
        clear(v)
    }

    private fun updateDisplay(v: View) {
        val display = v.rootView.findViewById<TextView>(R.id.display)
        display.text = numbers.joinToString("")
    }

    private fun isValidNumber(c: Char): Boolean {
        val validPoint = c != '.' || !numbers.contains('.')
        val notStartWithZero = if(numbers.isEmpty()) c != '.' else true
        val nonZeroAtBeginning = numbers.isNotEmpty() || c != '0'
        val notTooLong = numbers.size < 15

        return validPoint && notStartWithZero && nonZeroAtBeginning && notTooLong
    }
}