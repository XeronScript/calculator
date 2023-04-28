package com.example.calculator

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        if (Build.VERSION.SDK_INT >= 21) {
            val window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = this.resources.getColor(R.color.greyDarker)
        }

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
