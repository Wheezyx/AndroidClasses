package pl.wedel.lab1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun calculate(view: View) {
        val firstNumber: Spinner = findViewById(R.id.first_number)
        val secondNumber: Spinner = findViewById(R.id.second_number)
        val operation: Spinner = findViewById(R.id.operation)
        val textBox: TextView = findViewById(R.id.result)


        val number1 = Integer.valueOf(firstNumber.selectedItem.toString())
        val number2 = Integer.valueOf(secondNumber.selectedItem.toString())
        val oper = operation.selectedItem.toString()
        when (oper) {
            "+" -> textBox.text = (number1 + number2).toString()
            "-" -> textBox.text = (number1 - number2).toString()
            "*" -> textBox.text = (number1 * number2).toString()
            "/" -> textBox.text = (number1 / number2).toString()
        }
    }
}
