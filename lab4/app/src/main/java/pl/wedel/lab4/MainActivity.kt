package pl.wedel.lab4

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import pl.wedel.lab4.database.DBHelper
import pl.wedel.lab4.fragments.AddSkillFragment
import pl.wedel.lab4.fragments.ClassRaceAddFragment
import pl.wedel.lab4.service.MusicService

class MainActivity : AppCompatActivity(), AddSkillFragment.OnFragmentInteractionListener,
    ClassRaceAddFragment.OnFragmentInteractionListener {

    lateinit var saveFragment: AddSkillFragment
    lateinit var raceClassFragment: ClassRaceAddFragment
    private lateinit var DBHelper: DBHelper
    private var isBound = false
    private var musicService: MusicService? = null

    private val serviceCon = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            musicService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            service as MusicService.ServiceBinder
            musicService = service.service
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)
        if (intent.hasExtra("SAVE_MESSAGE"))
            Toast.makeText(applicationContext, intent.getStringExtra("SAVE_MESSAGE"), Toast.LENGTH_SHORT).show()

        DBHelper = DBHelper(this)

        var adapter: RaceAdapter? = null
        val raceList: ArrayList<Race> = DBHelper.readAllRaces()
        adapter = RaceAdapter(this, raceList)

        raceListView.adapter = adapter

        raceListView.setOnItemClickListener { _, _, i, l ->
            val intent = Intent(this, ClassActivity::class.java)
            intent.putExtra("race", raceList[i].name)
            startActivity(intent)
        }

        saveFragment = AddSkillFragment.newInstance()
        raceClassFragment = ClassRaceAddFragment.newInstance("A", "B")

        doBindService()
        val music = Intent()
        music.setClass(this, MusicService::class.java)
        startService(music)
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
                raceClassFragment.arguments?.putString("SAVE", "RACE")
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, raceClassFragment)
                    .addToBackStack(saveFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }
            R.id.addClass -> {
                raceClassFragment.arguments?.putString("SAVE", "CLASS")
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, raceClassFragment)
                    .addToBackStack(saveFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }
            R.id.addSkill -> {
                //TODO IMPLEMENT ADDING SKILL
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, saveFragment)
                    .addToBackStack(saveFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFragmentInteraction(uri: Uri) {
        Log.d("Changed", "Changed")
    }

    fun doBindService() {
        bindService(Intent(this, MusicService::class.java), serviceCon, Context.BIND_AUTO_CREATE)
        isBound = true
    }

    fun doUnbindService() {
        unbindService(serviceCon)
        isBound = false
    }

    override fun onResume() {
        super.onResume()
        musicService?.resumeMusic()
    }


}



