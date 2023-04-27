package com.example.calculator

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

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

            if (!equationQueue.last().endsWith('%') && isFloatOrInt(equationQueue.last())) {
                equationQueue[equationQueue.lastIndex] =
                    "${equationQueue[equationQueue.lastIndex]}%"
                digitClicks++
            }

            updateEquationView()
        }

        buttonSqrt.setOnClickListener {
            if (equationQueue.isEmpty()) {
                equationQueue.add((it as Button).text.toString())
                digitClicks++
                updateEquationView()
                return@setOnClickListener
            }

            val operations = arrayListOf("+", "-", "*", "/")

            if (equationQueue.last() in operations) {
                equationQueue.add((it as Button).text.toString())
                digitClicks++
            }

            updateEquationView()
        }

        buttonXPow.setOnClickListener {
            if (equationQueue.isEmpty() || equationQueue.last().contains("^"))
                return@setOnClickListener

            val li = equationQueue.lastIndex
            if (!isSpecialTyping && !equationQueue.last().contains("^2") &&
                (equationQueue[li].last().isDigit() || equationQueue[li].endsWith(")")) &&
                !equationQueue.last().contains(getString(R.string.sqrt))
            ) {
                equationQueue[li] = "${equationQueue[li]}^2"
            }

            updateEquationView()
        }

        buttonXPowY.setOnClickListener {
            if (equationQueue.isEmpty() || equationQueue.last().contains("^"))
                return@setOnClickListener

            val li = equationQueue.lastIndex
            if (!isSpecialTyping && !equationQueue.last().contains("^y") &&
                (equationQueue[li].last().isDigit() || equationQueue[li].endsWith(")")) &&
                    !equationQueue.last().contains(getString(R.string.sqrt))
            ) {
                equationQueue[li] = "${equationQueue[li]}^"
            }

            Log.d("equationQueue: ", equationQueue.toString())

            updateEquationView()
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

    private fun isFloatOrInt(str: String): Boolean {
        var pass = 0

        try {
            str.toInt()
            pass++
        } catch (_: java.lang.NumberFormatException) {}

        try {
            str.toFloat()
            pass++
        } catch (_: java.lang.NumberFormatException) {}


        return pass == 2
    }

    private fun onEqualsClick() {
        if (equationQueue.isEmpty() && specialOp.isEmpty())
            return

//        if (!valid())
//            DynamicToast.makeError(this, "Invalid operation", Toast.LENGTH_SHORT)

        var res: Float = 0f
        var eval: Float = 0f
        var isNum: Boolean = false
        var currOperation: String = "+"
        val myMap = equationQueue.mapIndexed { index: Int, s: String -> index to s }.toMap()
        val queueMerge: MutableList<String> = arrayListOf()


        Log.d("= Operations: ", equationQueue.toString())
        Log.d("= Special operations: ", specialOp.toString())

        myMap.forEach { (i, op) ->
            if (op.startsWith('s')) {
                val opIndex = op.slice(1 until op.length).toInt()
                queueMerge.add(specialOp[opIndex])
            } else {
                queueMerge.add(op)
            }
        }

        Log.d("= queueMerge: ", queueMerge.toString())

        for (str in queueMerge)
        {
            if (isFunction(str)) {
                val operation = str.split("(")[0]
                val value = str.split("(")[1]
                eval = calcTrig(operation, value)
                isNum = true
            } else if (isBasicOperation(str)) {
                currOperation = str
                isNum = false

            } else if (isPercentage(str)) {
                eval = (res / 100) * str.replace("%", "").toFloat()
                isNum = true

            } else if (isSquareRoot(str)) {
                eval = sqrt(str.replace("√", "").toFloat())
                isNum = true

            } else if (isPower(str)) {
                val base = str.split("^")[0]
                val exponent = str.split("^")[1]
                eval = base.toFloat().pow(exponent.toFloat())
                isNum = true

            } else {
                eval = str.toFloat()
                isNum = true
            }


            if (isNum) {
                if (currOperation.compareTo("+") == 0) {
                    res += eval
                }
                else if (currOperation.compareTo("-") == 0) {
                    res -= eval
                }
                else if (currOperation.compareTo("*") == 0) {
                    res *= eval
                }
                else if (currOperation.compareTo("/") == 0) {
                    res /= eval
                }
            }
        }

        val toPrint = trim(res.toString(), res)

        solutionView.text = toPrint
    }

    private fun trim(str: String, value: Float): String {
        if (str.endsWith(".0"))
            return str.split(".")[0]

        return str
    }

    private fun calcTrig(operation: String, value: String): Float {
        var num = 0f

        try {
            num = value.toFloat()
        } catch(_: NumberFormatException) {}

        if (operation.compareTo("sin") == 0) {
            Log.d("sin", sin(num).toString())
            return sin(num)
        }
        else if (operation.compareTo("cos") == 0) {
            Log.d("sin", cos(num).toString())
            return cos(num)
        }
        else if (operation.compareTo("tan") == 0) {
            Log.d("sin", tan(num).toString())
            return tan(num)
        }
        else if (operation.compareTo("log") == 0) {
            Log.d("sin", log10(num).toString())
            return log10(num)
        }
        else if (operation.compareTo("ln") == 0) {
            Log.d("sin", ln(num).toString())
            return ln(num)
        }

        return num
    }

    private fun isFunction(str: String): Boolean {
        val funcs = arrayListOf("cos(", "sin(", "tan(", "log(", "ln(")

        for (f in funcs)
            if (str.startsWith(f))
                return true

        return false
    }

    private fun isPower(str: String): Boolean {
        if (str.contains("^"))
            return true
        return false
    }

    private fun isSquareRoot(str: String): Boolean {
        if (str.startsWith("√"))
            return true
        return false
    }

    private fun isPercentage(str: String): Boolean {
        if (str.endsWith("%"))
            return true
        return false
    }

    private fun isBasicOperation(str: String): Boolean {
        val operations = arrayListOf("+", "-", "*", "/")

        for (op in operations)
            if (str.startsWith(op))
                return true

        return false
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun onChangeSign() {
        if (equationQueue.isEmpty() && specialOp.isEmpty())
            return
4
        // Changing sign of a number
        if (!isSpecialTyping && equationQueue.isNotEmpty()) {
            val li = equationQueue.lastIndex
            var isPow = false

            if (equationQueue.last().contains("^2"))
                isPow = true

            val prefix = equationQueue.last().substringBefore("^").replace("(", "").replace(")", "")
            val postfix = equationQueue.last().substringAfter("^")
            val ops = arrayListOf("+", "-", "*", "/")

            if (prefix.contains(getString(R.string.sqrt)))
                return

            if (prefix !in ops) {
                if (prefix.toInt() > 0)
                    equationQueue[li] = "(${prefix.toInt() * -1})"
                else
                    equationQueue[li] = "${prefix.toInt() * -1}"
            } else
                return

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

        val lastSpecialOp = if (specialOp.isNotEmpty()) specialOp.last() else ""
        val lastDigit = if (equationQueue.isNotEmpty()) equationQueue.last() else ""

        if (isSpecialTyping && validSpecialOpDot(lastSpecialOp))
            specialOp[specialOp.lastIndex] = "$lastSpecialOp."
        else if (!isSpecialTyping && lastDigit.isDigitsOnly())
            equationQueue[equationQueue.lastIndex] = "$lastDigit."
        else if (!isSpecialTyping && lastDigit[0].compareTo('√') == 0 && isFloatOrInt(lastDigit.substring(1)))
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

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun onOperationClick(it: View) {
        if (digitClicks > 0 && equationQueue.last().compareTo(getString(R.string.sqrt)) != 0 &&
                equationQueue.last().last() != '^') {
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
        if ((equationQueue.isNotEmpty() && equationQueue.last().contains("^2")) ||
            (equationQueue.isNotEmpty() && equationQueue.last().endsWith("%")))
            return

        val btn = it as Button
        val btnText = btn.text.toString()

        if (!isSpecialTyping) {
            if (digitClicks > 0) {
                if (equationQueue.last().last() != ')')
                    equationQueue[equationQueue.lastIndex] = equationQueue.last() + btnText
                else
                    equationQueue[equationQueue.lastIndex] = equationQueue.last().replace(")", btnText) + ')'
            }
            else
                equationQueue.add(btnText)
        } else
            specialOp[specialOp.lastIndex] = specialOp[specialOp.lastIndex] + btnText

        digitClicks++
        updateEquationView()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private fun updateEquationView() {
        equationView.text = extractEquation()
    }

    private fun extractEquation(): String {
        var res = ""

        Log.d("ex equationQueue: ", equationQueue.toString())
        Log.d("ex specialOp: ", specialOp.toString())

        for (i in equationQueue.indices) {
            res += if (equationQueue[i].startsWith("s") && !equationQueue[i].startsWith("sin")) {
                val opIndex = equationQueue[i].slice(1 until equationQueue[i].length).toInt()
                "${specialOp[opIndex]}) "
            } else if (equationQueue[i].startsWith("sin")) {
                "${equationQueue[i]}) "
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
