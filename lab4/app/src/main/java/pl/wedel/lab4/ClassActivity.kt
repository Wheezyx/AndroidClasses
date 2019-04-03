package pl.wedel.lab4

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_class.*


class ClassActivity : AppCompatActivity() {

    private lateinit var race: Race

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        setSupportActionBar(toolbar as Toolbar?)
        race = intent.getSerializableExtra("race") as Race
        Toast.makeText(applicationContext, "You have selected $race", Toast.LENGTH_SHORT).show()
        var adapter: ClassAdapter? = null
        val classList: Array<Class> = Class.values()
        adapter = ClassAdapter(this, classList)


        classListView.adapter = adapter
        classListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, SkillActivity::class.java)
            intent.putExtra("race", race)
            intent.putExtra("class", classList[i])
            startActivity(intent)
        }

    }
}
