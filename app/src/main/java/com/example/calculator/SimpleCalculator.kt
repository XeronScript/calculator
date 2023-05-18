package com.example.calculator

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import javax.script.ScriptEngineManager

class SimpleCalculator : AppCompatActivity() {

    private lateinit var equationView: TextView
    private lateinit var solutionView: TextView
    private val numbers: MutableList<String> = arrayListOf()
    private val operators: MutableList<String> = arrayListOf()
    private var onClick: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_calculator)

        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(67108864)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)

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
    }

    private fun onChangeSign() {
        if (numbers.isEmpty())
            return

        numbers[numbers.lastIndex] = numbers[numbers.lastIndex]
            .replace("(", "")
            .replace(")", "")

        if (numbers[numbers.lastIndex].toInt() > 0)
            numbers[numbers.lastIndex] = "(${(numbers.last().toInt() * -1)})"
        else
            numbers[numbers.lastIndex] = "${(numbers.last().toInt() * -1)}"
        updateEquationView()
    }

    private fun onEqualsClick() {
        if (numbers.size == operators.size) {
            DynamicToast.makeError(this, "Invalid Equation", Toast.LENGTH_SHORT).show()
            return
        }

        val engine = ScriptEngineManager().getEngineByName("rhino")
        val res = engine.eval(extractEquation()).toString()

        if (Integer.parseInt(res.last().toString()) == 0)
            solutionView.text = (res.split("."))[0]
        else
            solutionView.text = res
    }

    private fun extractEquation(): String {
        var res = ""
        val max =
            if (numbers.size > operators.size) numbers.size - 1
            else operators.size - 1

        for (i in 0..max) {
            res += numbers[i]

            try {
                res += " " + operators[i] + " "
            } catch (err: java.lang.IndexOutOfBoundsException) {
                continue
            }
        }

        return res
    }

    private fun updateEquationView() {
        equationView.text = extractEquation()
    }

    private fun onDigitClick(view: View) {
        val btn = view as Button
        val btnText = btn.text.toString()

        if (onClick > 0) {
            numbers[numbers.lastIndex] = numbers.last() + btnText
        } else {
            numbers.add(btnText)
            onClick++
        }

        updateEquationView()
    }

    private fun onOperationClick(view: View) {
        if (onClick > 0) {
            operators.add((view as Button).text.toString())
            onClick = 0
            updateEquationView()
        }
    }

    private fun onDotClick(view: View) {
        if (onClick > 0) {
            val btn = view as Button

            if (!numbers.last().contains(".")) {
                numbers[numbers.lastIndex] += btn.text.toString()
                updateEquationView()
            } else
                return
        }
    }

    private fun clearEquationView() {
        numbers.clear()
        operators.clear()
        equationView.text = ""
        onClick = 0
    }

    private fun clearAllViews() {
        solutionView.text = ""
        clearEquationView()
    }
}
