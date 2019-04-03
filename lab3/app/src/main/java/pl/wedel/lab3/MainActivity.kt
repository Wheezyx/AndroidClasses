package pl.wedel.lab3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mainLayout.setBackgroundColor(Color.GREEN)
            } else {
                mainLayout.setBackgroundColor(Color.WHITE)
            }
        }

    }

    fun sayHello(view: View) {
        val message :String = messageSpinner.selectedItem.toString()
        this.toast("$message. Miło Cię widzieć!")
    }

    fun changeActivity(view: View){
        val intent = Intent(this, SecondAcitvity::class.java)
        startActivity(intent)
    }


}


fun Context.toast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun String.anagram() =
    System.out.print(this)
