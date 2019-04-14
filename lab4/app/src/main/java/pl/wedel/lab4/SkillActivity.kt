package pl.wedel.lab4

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_skill.*
import pl.wedel.lab4.database.DBHelper

class SkillActivity : AppCompatActivity() {

    private lateinit var race: String
    private lateinit var klass: String

    lateinit var DBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        setSupportActionBar(toolbar as Toolbar?)
        race = intent.getStringExtra("race")
        klass = intent.getStringExtra("class")
        Toast.makeText(applicationContext, "You have selected $race and $klass", Toast.LENGTH_SHORT).show()

        DBHelper = DBHelper(this)
        var adapter: SkillAdapter

        val flatSkillList = DBHelper.readAllSkillsByRaceAndClass(race, klass)

        adapter = SkillAdapter(this, flatSkillList)

        skillListView.adapter = adapter
        skillListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, SkillDetailsActivity::class.java)
            intent.putExtra("skill", flatSkillList[i].id)
            startActivity(intent)
        }

    }


}
