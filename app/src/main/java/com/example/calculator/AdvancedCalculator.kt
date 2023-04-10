package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sin

class AdvancedCalculator : AppCompatActivity() {

    private lateinit var equationView: TextView
    private lateinit var solutionView: TextView
    private val eq_queue: MutableList<String> = arrayListOf()
    private var onClick: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advanced_calculator)

        equationView = findViewById(R.id.equation_view)
        solutionView = findViewById(R.id.solution_view)

        var clicks: Short = 0

        val buttonOne = findViewById<Button>(R.id.button_1)
        val buttonTwo = findViewById<Button>(R.id.button_2)
        val buttonThree = findViewById<Button>(R.id.button_3)
        val buttonFour = findViewById<Button>(R.id.button_4)
        val buttonFive = findViewById<Button>(R.id.button_5)
        val buttonSix = findViewById<Button>(R.id.button_6)
        val buttonSeven = findViewById<Button>(R.id.button_7)
        val buttonEight = findViewById<Button>(R.id.button_8)
        val buttonNine = findViewById<Button>(R.id.button_9)
        val buttonZero = findViewById<Button>(R.id.button_0)
        val buttonDivide = findViewById<Button>(R.id.button_divide)
        val buttonMultiply = findViewById<Button>(R.id.button_multiply)
        val buttonSubtract = findViewById<Button>(R.id.button_subtract)
        val buttonAdd = findViewById<Button>(R.id.button_add)
        val buttonDot = findViewById<Button>(R.id.button_dot)
        val buttonAC = findViewById<Button>(R.id.button_ac)
        val buttonC = findViewById<TextView>(R.id.button_c)
        val buttonEquals = findViewById<Button>(R.id.button_equals)
        val buttonChangeSign = findViewById<Button>(R.id.change_sign)
        val buttonPercentage = findViewById<Button>(R.id.button_percent)
        val buttonSqrt = findViewById<Button>(R.id.button_sqrt)
        val buttonXPow = findViewById<Button>(R.id.button_xpow)
        val buttonXPowY = findViewById<Button>(R.id.button_xpowy)
        val buttonSin = findViewById<Button>(R.id.button_sin)
        val buttonCos = findViewById<Button>(R.id.button_cos)
        val buttonTan = findViewById<Button>(R.id.button_tan)
        val buttonLog = findViewById<Button>(R.id.button_log)
        val buttonLn = findViewById<Button>(R.id.button_ln)

        buttonOne.setOnClickListener {
            onDigitClick(it)
        }

        buttonTwo.setOnClickListener {
            onDigitClick(it)
        }

        buttonThree.setOnClickListener {
            onDigitClick(it)
        }

        buttonFour.setOnClickListener {
            onDigitClick(it)
        }

        buttonFive.setOnClickListener {
            onDigitClick(it)
        }

        buttonSix.setOnClickListener {
            onDigitClick(it)
        }

        buttonSeven.setOnClickListener {
            onDigitClick(it)
        }

        buttonEight.setOnClickListener {
            onDigitClick(it)
        }

        buttonNine.setOnClickListener {
            onDigitClick(it)
        }

        buttonZero.setOnClickListener {
            onDigitClick(it)
        }

        buttonC.setOnClickListener {
            clicks++

            if (clicks < 2) {
                clearEquationView()
            } else {
                clearAllViews()
                clicks = 0
            }
        }

        buttonAC.setOnClickListener {
            clearAllViews()
        }

        buttonDivide.setOnClickListener {
            onOperationClick(it)
        }

        buttonMultiply.setOnClickListener {
            onOperationClick(it)
        }

        buttonAdd.setOnClickListener {
            onOperationClick(it)
        }

        buttonSubtract.setOnClickListener {
            onOperationClick(it)
        }

        buttonEquals.setOnClickListener {
            onEqualsClick()
        }

        buttonDot.setOnClickListener {
            onDotClick(it)
        }

        buttonChangeSign.setOnClickListener {
            onChangeSign()
        }

        buttonPercentage.setOnClickListener {
            onOperationClick(it)
        }

        buttonSqrt.setOnClickListener {
            onOperationClick(it)
        }

        buttonXPow.setOnClickListener {
            onOperationClick(it)
        }

        buttonXPowY.setOnClickListener {
            onOperationClick(it)
        }

        buttonSin.setOnClickListener {
            onOperationClick(it)
        }

        buttonCos.setOnClickListener {
            onOperationClick(it)
        }

        buttonTan.setOnClickListener {
            onOperationClick(it)
        }

        buttonLog.setOnClickListener {
            onOperationClick(it)
        }

        buttonLn.setOnClickListener {
            onOperationClick(it)
        }

    }

    private fun onEqualsClick() {
        Log.d("Sin: ", sin((-90).toDouble()).toString())
    }

    private fun onChangeSign() {

    }

    private fun onDotClick(it: View) {

    }

    private fun onOperationClick(it: View) {

    }

    private fun onDigitClick(it: View) {
        val btn = it as Button
        val btnText = btn.text.toString()

//        TODO("add number to whole equation queue")

        updateEquationView()
    }

    private fun updateEquationView() {
        equationView.text = extractEquation()
    }

    private fun extractEquation(): String {
        return eq_queue.joinToString(" ")
    }

    private fun clearEquationView() {
        eq_queue.clear()
        equationView.text = ""
//        onClick = 0
    }

    private fun clearAllViews() {
        solutionView.text = ""
        clearEquationView()
    }
}
