package pl.wedel.lab4

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_class.*
import pl.wedel.lab4.database.DBHelper


class ClassActivity : AppCompatActivity() {

    private lateinit var race: String
    lateinit var DBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class)
        setSupportActionBar(toolbar as Toolbar?)
        race = intent.getStringExtra("race")
        Toast.makeText(applicationContext, "You have selected $race", Toast.LENGTH_SHORT).show()

        DBHelper = DBHelper(this)

        var adapter: ClassAdapter? = null
        val classList: ArrayList<Class> = DBHelper.readAllClasses()
        adapter = ClassAdapter(this, classList)


        classListView.adapter = adapter
        classListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, SkillActivity::class.java)
            intent.putExtra("race", race)
            intent.putExtra("class", classList[i].name)
            startActivity(intent)
        }

    }
}
