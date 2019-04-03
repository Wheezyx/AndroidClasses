package pl.wedel.lab3

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_second_acitvity.*

class SecondAcitvity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_acitvity)

    }


    fun changePet(view: View) {
        val id: Int = petRadioGroup.checkedRadioButtonId
        if (id == -1)
        {
            this.toast("Nic nie wybrałeś!")
            return
        }
        val radio: RadioButton = findViewById(id)
        val petName = radio.text.toString().toLowerCase()
        val imageId = resources.getIdentifier(petName, "drawable", packageName)
        pewImageView.setImageResource(imageId)
    }


    fun changeToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)
        //intent.putExtra("message", "Hello from Main Acitivty!")
        startActivity(intent)
    }
}
