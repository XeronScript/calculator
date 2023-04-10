package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val btnSimple = findViewById<Button>(R.id.simple)
        val btnAdvanced = findViewById<Button>(R.id.advanced)
        val btnAbout = findViewById<Button>(R.id.about)
        val btnExit = findViewById<Button>(R.id.exit)

        btnExit.setOnClickListener {
            finish()
            exitProcess(0)
        }

        btnSimple.setOnClickListener {
            val simple_calc = Intent(this, SimpleCalculator::class.java)
            startActivity(simple_calc)
        }

        btnAdvanced.setOnClickListener {
            val advancedCalc = Intent(this, AdvancedCalculator::class.java)
            startActivity(advancedCalc)
        }

        btnAbout.setOnClickListener {
            val about = Intent(this, About::class.java)
            startActivity(about)
        }
    }
}
