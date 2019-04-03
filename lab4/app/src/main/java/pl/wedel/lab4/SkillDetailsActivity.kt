package pl.wedel.lab4

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_skill_details.*
import java.lang.StringBuilder

class SkillDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_details)

        val skill = intent.getSerializableExtra("skill") as Skill
        skillName.text = skill.name
        skillDescription.text = skill.description
        skillImage.setImageResource(skill.image)
        skillLevel.text = skill.levelRequired.toString()
        skillAvailableFor.text = skill.classRequired.name + "\n" + getRaces(skill.raceRequired)
    }

    private fun getRaces(raceRequired: Array<Race>): Any? {
        val sb = StringBuilder()
        raceRequired.forEach { race -> sb.append(race.name + "\n") }
        return sb.toString()
    }
}

