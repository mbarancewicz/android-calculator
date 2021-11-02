package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

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
}