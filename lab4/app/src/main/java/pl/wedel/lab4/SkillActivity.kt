package pl.wedel.lab4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_skill.*

class SkillActivity : AppCompatActivity() {

    private lateinit var race: Race
    private lateinit var klass: Class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        setSupportActionBar(toolbar as Toolbar?)
        race = intent.getSerializableExtra("race") as Race
        klass = intent.getSerializableExtra("class") as Class
        Toast.makeText(applicationContext, "You have selected $race and $klass", Toast.LENGTH_SHORT).show()
        var adapter: SkillAdapter? = null
        val skillList: List<Skill> = getAllSkills()

        val flatSkillList = skillList.asSequence()
            .filter { skill -> skill.classRequired == klass }
            .filter { skill -> race in skill.raceRequired }
            .toList()

        adapter = SkillAdapter(this, flatSkillList)

        skillListView.adapter = adapter
        skillListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, SkillDetailsActivity::class.java)
            intent.putExtra("skill", flatSkillList[i])
            startActivity(intent)
        }

    }


}

private fun getAllSkills(): List<Skill> {
    return arrayListOf(
        Skill(
            "Fireball",
            "Throw a powerfull ball of fire",
            12,
            R.drawable.fireball,
            Class.MAGE,
            arrayOf(Race.HUMAN, Race.ORC)
        ),
        Skill(
            "Blade dance",
            "Cut your enemies",
            3,
            R.drawable.blade_dance,
            Class.WARRIOR,
            arrayOf(Race.HUMAN, Race.ORC)
        )
    )
}

