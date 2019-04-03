package pl.wedel.lab4

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        var adapter: RaceAdapter? = null
        val raceList: Array<Race> = Race.values()
        adapter = RaceAdapter(this, raceList)

        raceListView.adapter = adapter

        raceListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, ClassActivity::class.java)
            intent.putExtra("race", raceList[i])
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.about -> {
                startActivity(Intent(this, AuthorActivity::class.java))
                return true
            }
            R.id.addRace -> {
                Toast.makeText(applicationContext, "Not implemented yet!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.addClass -> {
                Toast.makeText(applicationContext, "Not implemented yet!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.addSkill -> {
                Toast.makeText(applicationContext, "Not implemented yet!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }




}
