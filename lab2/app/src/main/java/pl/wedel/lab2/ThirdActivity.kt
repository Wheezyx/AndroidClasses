package pl.wedel.lab2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_third.*

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val message = intent.getStringExtra("message")
        messageTextView.text = message
    }


    fun onActivityChange(view: View) {
        val intent = Intent(this, AcitivityUtils.getIntent(view.id))
        intent.putExtra("message", "Hello from third Acitivty!")
        startActivity(intent)
    }
}
