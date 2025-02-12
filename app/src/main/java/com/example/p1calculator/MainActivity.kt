package com.example.p1calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var butDel: Button
    private lateinit var butC: Button
    private lateinit var butCE: Button
    private lateinit var butDiv: Button
    private lateinit var butMult: Button
    private lateinit var butMinus: Button
    private lateinit var butPlus: Button
    private lateinit var butEquals: Button
    private lateinit var butPoint: Button

    private lateinit var display: TextView
    private lateinit var displayAccumulated: TextView

    private var operationToDo : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeUI()

        butPoint.setOnClickListener{
            addPointDisplay()
        }
        butC.setOnClickListener{
            onClickC()
        }
        butCE.setOnClickListener{
            onClickCE()
        }
        butDel.setOnClickListener{
            onClickDel()
        }
        butDiv.setOnClickListener{
            onClickOperation("÷")
        }
        butMult.setOnClickListener{
            onClickOperation("x")
        }
        butMinus.setOnClickListener{
            onClickOperation("-")
        }
        butPlus.setOnClickListener{
            onClickOperation("+")
        }
        butEquals.setOnClickListener{
            onClickEquals()
        }

    }

    private fun onClickDel() {
        display.text = display.text.toString().dropLast(1)
    }

    private fun onClickCE() {
        display.text = ""
        displayAccumulated.text = ""
        operationToDo = ""
    }

    private fun onClickEquals() {
        if(display.text.isEmpty()) return

        operationToDo += display.text.toString()

        display.text = evaluateExpression(operationToDo).take(12)

        displayAccumulated.text = ""
        operationToDo = ""
    }

    private fun onClickOperation(op : String){
        if(display.text.isEmpty()) return
        displayAccumulated.text = displayAccumulated.text.toString() + display.text.toString() + op
        operationToDo += display.text.toString() + when(op){
            "+" -> "+"
            "-" -> "-"
            "÷" -> "/"
            "x" -> "*"
            else -> op
        }
        display.text = ""
    }

    private fun initializeUI(){
        butDel = findViewById(R.id.butDel)
        butC = findViewById(R.id.butC)
        butCE = findViewById(R.id.butCE)
        butDiv = findViewById(R.id.butDiv)
        butMult = findViewById(R.id.butMult)
        butMinus = findViewById(R.id.butMinus)
        butPlus = findViewById(R.id.butPlus)
        butEquals = findViewById(R.id.butEquals)
        butPoint = findViewById(R.id.butPoint)

        display = findViewById(R.id.txtViewDisplay)
        displayAccumulated = findViewById(R.id.txtViewAccumulated)
    }

    fun addNumberDisplay(view : View){
        val btnNum = view as? Button ?: return
        display.text = display.text.toString() + btnNum.text.toString()
    }

    private fun addPointDisplay(){
        var numStr : String = display.text.toString()
        if(numStr.contains(".")) return
        if(numStr.isEmpty()) numStr = "0"
        numStr += "."

        display.text = numStr

    }

    private fun onClickC(){
        display.text = ""
    }

    fun evaluateExpression(expression: String): String {
        return try {
            val result = ExpressionBuilder(expression).build().evaluate()
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }
}