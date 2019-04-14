package pl.wedel.lab4

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_skill_details.*
import pl.wedel.lab4.database.DBHelper

class SkillDetailsActivity : AppCompatActivity() {

    lateinit var DBHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill_details)


        DBHelper = DBHelper(this)
        val skillId = intent.getIntExtra("skill", 1)
        val skill = DBHelper.getSkillyId(skillId)

        skillName.text = skill.name
        skillDescription.text = skill.description
        skillImage.setImageBitmap(skill.image)
        skillLevel.text = skill.levelRequired.toString()
        skillAvailableFor.text = skill.name + "\n" + skill.raceRequired
    }
}

