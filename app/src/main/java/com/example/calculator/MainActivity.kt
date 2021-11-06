package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.calc.ExtendedCalcActivity
import com.example.calculator.calc.StandardCalcActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun closeApp(view: View) {
        finishAndRemoveTask()
    }

    fun startStandardCalc(view: View) {
        val intent = Intent(this, StandardCalcActivity::class.java)
        startActivity(intent)
    }

    fun startExtendedCalc(view: View) {
        val intent = Intent(this, ExtendedCalcActivity::class.java)
        startActivity(intent)
    }

    fun startAbout(view: View) {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }
}