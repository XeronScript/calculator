package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly

class AdvancedCalculator : AppCompatActivity() {

    private lateinit var equationView: TextView
    private lateinit var solutionView: TextView
    private val equationQueue: MutableList<String> = arrayListOf()
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
            onDotClick()
        }

        buttonChangeSign.setOnClickListener {
            onChangeSign()
        }

        buttonPercentage.setOnClickListener {
            if (equationQueue.isEmpty())
                return@setOnClickListener

            if (!equationQueue[equationQueue.lastIndex].endsWith('%'))
                equationQueue[equationQueue.lastIndex] =
                    "${equationQueue[equationQueue.lastIndex]}%"

            updateEquationView()
        }

        buttonSqrt.setOnClickListener {
            if (equationQueue.isEmpty()) {
                equationQueue.add((it as Button).text.toString())
                return@setOnClickListener
            }

            val operations = arrayListOf("+", "-", "*", "/")

            if (equationQueue[equationQueue.lastIndex] in operations)
                equationQueue.add((it as Button).text.toString())

            updateEquationView()
        }

        buttonXPow.setOnClickListener {
            if (equationQueue.isEmpty() || equationQueue[equationQueue.lastIndex].contains("^"))
                return@setOnClickListener

            val li = equationQueue.lastIndex
            if (!isSpecialTyping && !equationQueue.last().contains("^2") &&
                (equationQueue[li].last().isDigit() || equationQueue[li].endsWith(")"))
            ) {
                equationQueue[li] = "${equationQueue[li]}^2"
            }

            updateEquationView()
        }

        buttonXPowY.setOnClickListener {
            // TODO implement own listener
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
        Log.d("equationQueue: ", equationQueue.toString())
        Log.d("specialOp: ", specialOp.toString())
    }

    private fun onChangeSign() {
        if (equationQueue.isEmpty() && specialOp.isEmpty())
            return

        // Changing sign of a number
        if (!isSpecialTyping && equationQueue.isNotEmpty()) {
            val li = equationQueue.lastIndex
            var isPow = false

            if (equationQueue.last().contains("^2"))
                isPow = true

            val prefix = equationQueue.last().substringBefore("^").replace("(", "").replace(")", "")
            val postfix = equationQueue.last().substringAfter("^")
//            equationQueue[li].replace("^${postfix}", "")

            Log.d("Prefix", prefix)
            Log.d("Postfix", postfix)
            Log.d("equationQeueu", equationQueue.toString())

            equationQueue[li] = equationQueue[li]
                .replace("(", "")
                .replace(")", "")

            if (prefix.toInt() > 0)
                equationQueue[li] = "(${prefix.toInt() * -1})"
            else
                equationQueue[li] = "${prefix.toInt() * -1}"

            if (isPow)
                equationQueue[li] = "${equationQueue[li]}^${postfix}"
        }
        // Changing sign of a number in trigonometric expression
        else if (isSpecialTyping && specialOp.isNotEmpty()) {
            val lastSpecialOp = specialOp[specialOp.lastIndex]
            val trigs = arrayListOf("cos(", "sin(", "tan(", "log(")

            if (lastSpecialOp.startsWith("ln") && lastSpecialOp.length == 3)
                return
            else if (lastSpecialOp in trigs && lastSpecialOp.length == 4)
                return

            if (lastSpecialOp.startsWith("ln")) {
                if (lastSpecialOp.length > 3 && lastSpecialOp[3] == '-')
                    specialOp[specialOp.lastIndex] = lastSpecialOp.replace("-", "")
                else
                    specialOp[specialOp.lastIndex] = lastSpecialOp.replaceRange(3, 3, "-")
            } else {
                if (lastSpecialOp.length > 4 && lastSpecialOp[4] == '-')
                    specialOp[specialOp.lastIndex] = lastSpecialOp.replace("-", "")
                else
                    specialOp[specialOp.lastIndex] = lastSpecialOp.replaceRange(4, 4, "-")
            }
        }

        updateEquationView()
    }

    private fun onDotClick() {
        if (equationQueue.isEmpty() && specialOp.isEmpty())
            return

        val lastSpecialOp = if (specialOp.isNotEmpty()) specialOp[specialOp.lastIndex] else ""
        val lastDigit =
            if (equationQueue.isNotEmpty()) equationQueue[equationQueue.lastIndex] else ""

        if (isSpecialTyping && validSpecialOpDot(lastSpecialOp))
            specialOp[specialOp.lastIndex] = "$lastSpecialOp."
        else if (!isSpecialTyping && lastDigit.isDigitsOnly())
            equationQueue[equationQueue.lastIndex] = "$lastDigit."

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
//            if (isSpecialTyping)
//                specialOp[specialOp.lastIndex] = specialOp[specialOp.lastIndex] + ")"

            isSpecialTyping = false
            equationQueue.add((it as Button).text.toString())
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
            equationQueue.add("s${s++}")

            updateEquationView()
        }
    }

    private fun onDigitClick(it: View) {
        if (equationQueue.isNotEmpty() && equationQueue.last().contains("^2"))
            return

        val btn = it as Button
        val btnText = btn.text.toString()

        if (!isSpecialTyping) {
            if (digitClicks > 0)
                equationQueue[equationQueue.lastIndex] = equationQueue.last() + btnText
            else
                equationQueue.add(btnText)
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

        for (i in equationQueue.indices) {
            res += if (equationQueue[i].startsWith("s")) {
                val opIndex = equationQueue[i].slice(1 until equationQueue[i].length).toInt()
                "${specialOp[opIndex]}) "
            } else
                "${equationQueue[i]} "
        }

        return res
    }

    private fun clearEquationView() {
        equationQueue.clear()
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
