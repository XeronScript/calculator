package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import kotlin.math.PI
import kotlin.math.sin

class AdvancedCalculator : AppCompatActivity() {

    private lateinit var equationView: TextView
    private lateinit var solutionView: TextView
    private val eqQueue: MutableList<String> = arrayListOf()
    private val specialOp: MutableList<String> = arrayListOf()
    private var isSpecialTyping: Boolean = false
    private var digitClicks: Int = 0
    private var s: Int = 0


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
//            onSpecialOpClick(it)
            // TODO write percentage's own logic
        }

        buttonSqrt.setOnClickListener {
//            onSpecialOpClick(it)
            onDigitClick(it)
        }

        buttonXPow.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonXPowY.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonSin.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonCos.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonTan.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonLog.setOnClickListener {
            onSpecialOpClick(it)
        }

        buttonLn.setOnClickListener {
            onSpecialOpClick(it)
        }

    }

    private fun onEqualsClick() {
        Log.d("Test: ", "314".isDigitsOnly().toString())
    }

    private fun onChangeSign() {
        if (eqQueue.isEmpty())
            return

        eqQueue[eqQueue.lastIndex] = eqQueue[eqQueue.lastIndex]
            .replace("(", "")
            .replace(")", "")

        if (eqQueue[eqQueue.lastIndex].toInt() > 0)
            eqQueue[eqQueue.lastIndex] = "(${(eqQueue.last().toInt() * -1)})"
        else
            eqQueue[eqQueue.lastIndex] = "${(eqQueue.last().toInt() * -1)}"
        updateEquationView()
    }

    private fun onDotClick(it: View) {
        if (eqQueue.isEmpty() && specialOp.isEmpty())
            return

        val lastSpecialOp = if (specialOp.isNotEmpty()) specialOp[specialOp.lastIndex] else ""
        val lastDigit = if (eqQueue.isNotEmpty()) eqQueue[eqQueue.lastIndex] else ""

        if (isSpecialTyping && validSpecialOpDot(lastSpecialOp))
            specialOp[specialOp.lastIndex] =
                lastSpecialOp + (it as Button).text.toString()
        else if (!isSpecialTyping && lastDigit.isDigitsOnly())
            eqQueue[eqQueue.lastIndex] = lastDigit + (it as Button).text.toString()

        updateEquationView()
    }

    private fun validSpecialOpDot(lastSpecialOp: String): Boolean {
        if (lastSpecialOp.startsWith("ln") && lastSpecialOp.length > 3)
                return true
        else
            if (lastSpecialOp.length > 4)
                return true

        return false
    }

    private fun onOperationClick(it: View) {
        if (digitClicks > 0) {
            if (isSpecialTyping)
                specialOp[specialOp.lastIndex] = specialOp[specialOp.lastIndex] + ")"

            isSpecialTyping = false
            eqQueue.add((it as Button).text.toString())
            digitClicks = 0
            updateEquationView()
        }
    }

    private fun onSpecialOpClick(it: View) {
        if (digitClicks == 0) {
            isSpecialTyping = true
            digitClicks++

            val btn = (it as Button).text.toString()
            specialOp.add("$btn(")
            eqQueue.add("s${s++}")

            updateEquationView()
        }
    }

    private fun onDigitClick(it: View) {
        val btn = it as Button
        val btnText = btn.text.toString()

        if (!isSpecialTyping) {
            if (digitClicks > 0)
                eqQueue[eqQueue.lastIndex] = eqQueue.last() + btnText
            else
                eqQueue.add(btnText)
        } else
            specialOp[specialOp.lastIndex] = specialOp[specialOp.lastIndex] + btnText

        digitClicks++
        updateEquationView()
    }

    private fun updateEquationView() {
        equationView.text = extractEquation()
    }

    private fun extractEquation(): String {
        var res = ""

        for (i in eqQueue.indices) {
            res += if (eqQueue[i].startsWith("s")) {
                val opIndex = eqQueue[i].slice(1 until eqQueue[i].length).toInt()
                specialOp[opIndex]
            } else
                eqQueue[i]
        }

        return res
    }

    private fun clearEquationView() {
        eqQueue.clear()
        specialOp.clear()
        equationView.text = ""
        digitClicks = 0
        s = 0
        isSpecialTyping = false
    }

    private fun clearAllViews() {
        solutionView.text = ""
        clearEquationView()
    }
}
