package pl.wedel.lab4.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import pl.wedel.lab4.Class
import pl.wedel.lab4.R
import pl.wedel.lab4.Race
import pl.wedel.lab4.Skill
import java.io.ByteArrayOutputStream

class DBHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DROP_CLASS_TABLE)
        db.execSQL(SQL_DROP_RACE_TABLE)
        db.execSQL(SQL_DROP_SKILL_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CLASS_ENTRIES)
        db.execSQL(SQL_CREATE_RACE_ENTRIES)
        db.execSQL(SQL_CREATE_SKILL_ENTRIES)


        insertRace(
            Race(
                1,
                "HUMAN",
                "I like bears and chewing gums",
                BitmapFactory.decodeResource(context.resources, R.drawable.human_race)
            ), db
        )
        insertRace(
            Race(
                2,
                "ORC",
                "ME SMASH U!!!",
                BitmapFactory.decodeResource(context.resources, R.drawable.orc_race)
            ), db
        )
        insertRace(
            Race(
                3,
                "UNDEAD",
                "I am dead, am not I?",
                BitmapFactory.decodeResource(context.resources, R.drawable.undead_race)
            ), db
        )
        insertRace(
            Race(
                4,
                "ELF",
                "Wisdom is your strength",
                BitmapFactory.decodeResource(context.resources, R.drawable.elf_race)
            ), db
        )

        insertClass(
            Class(
                1,
                "WARRIOR",
                "WARRR!!!!",
                BitmapFactory.decodeResource(context.resources, R.drawable.warrior)
            ), db
        )
        insertClass(
            Class(
                2,
                "MAGE",
                "Should i fire you with a power of flames?",
                BitmapFactory.decodeResource(context.resources, R.drawable.mage)
            ), db
        )
        insertClass(
            Class(
                3,
                "ROGUE",
                "U dont know where i am....",
                BitmapFactory.decodeResource(context.resources, R.drawable.rogue)
            ), db
        )
        insertSkill(
            Skill(
                1,
                "Fireball",
                "Throw a powerfull ball of fire",
                12,
                BitmapFactory.decodeResource(context.resources, R.drawable.fireball),
                "MAGE",
                arrayListOf("HUMAN", "UNDEAD")
            ), db
        )
        insertSkill(
            Skill(
                2,
                "Blade dance",
                "Cut your enemies",
                3,
                BitmapFactory.decodeResource(context.resources, R.drawable.blade_dance),
                "WARRIOR",
                arrayListOf("HUMAN", "ORC")
            ), db
        )

    }


    fun readAllRaces(): ArrayList<Race> {
        val races = ArrayList<Race>()
        val db = writableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM RACES", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var raceId: Int
        var description: String
        var image: Bitmap
        var name: String

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                raceId = cursor.getInt(cursor.getColumnIndex("id"))
                description = cursor.getString(cursor.getColumnIndex("description"))

                val imageArray = cursor.getBlob(cursor.getColumnIndex("image"))
                image = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)

                name = cursor.getString(cursor.getColumnIndex("name"))
                races.add(Race(raceId, name, description, image))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return races
    }

    fun readAllClasses(): ArrayList<Class> {
        val classes = ArrayList<Class>()
        val db = writableDatabase

        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM CLASS", null)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var raceId: Int
        var description: String
        var image: Bitmap
        var name: String

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                raceId = cursor.getInt(cursor.getColumnIndex("id"))
                description = cursor.getString(cursor.getColumnIndex("description"))

                val imageArray = cursor.getBlob(cursor.getColumnIndex("image"))
                image = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)
                name = cursor.getString(cursor.getColumnIndex("name"))

                classes.add(Class(raceId, name, description, image))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return classes
    }

    fun readAllSkillsByRaceAndClass(race: String, clazz: String): ArrayList<Skill> {
        val skills = ArrayList<Skill>()
        val db = writableDatabase

        var cursor: Cursor? = null
        try {
            println("$race $clazz")

            cursor = db.rawQuery(
                "SELECT * FROM SKILL WHERE ? LIKE SKILL.CLASS AND SKILL.RACES LIKE ?", arrayOf(
                    clazz,
                    "%$race%"
                )
            )
            //AND ? LIKE races", arrayOf(clazz, race)
        } catch (e: SQLiteException) {
            return ArrayList()
        }

        var skillId: Int
        var description: String
        var image: Bitmap
        var name: String
        var classRequired: String
        var raceRequired: List<String>
        var levelRequired: Int
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                skillId = cursor.getInt(cursor.getColumnIndex("id"))
                description = cursor.getString(cursor.getColumnIndex("description"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                levelRequired = cursor.getInt(cursor.getColumnIndex("level"))
                classRequired = cursor.getString(cursor.getColumnIndex("class"))
                raceRequired = cursor.getString(cursor.getColumnIndex("races")).split(",")

                val imageArray = cursor.getBlob(cursor.getColumnIndex("image"))
                image = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)

                skills.add(
                    Skill(
                        skillId, name, description, levelRequired, image, classRequired,
                        raceRequired as ArrayList<String>
                    )
                )
                cursor.moveToNext()
            }
        }
        cursor.close()

        return skills
    }


    fun insertRace(race: Race, db: SQLiteDatabase = writableDatabase): Boolean {

        val values = ContentValues()
        values.put("id", race.id)
        values.put("name", race.name)
        values.put("description", race.description)

        val bos = ByteArrayOutputStream()
        race.image.compress(Bitmap.CompressFormat.PNG, 100, bos)

        values.put("image", bos.toByteArray())

        val newRowId = db.insert("RACES", null, values)
        return true
    }

    fun insertClass(clazz: Class, db: SQLiteDatabase = writableDatabase): Boolean {
        val values = ContentValues()
        values.put("id", clazz.id)
        values.put("name", clazz.name)
        values.put("description", clazz.description)

        val bos = ByteArrayOutputStream()
        clazz.image.compress(Bitmap.CompressFormat.PNG, 100, bos)

        values.put("image", bos.toByteArray())

        val newRowId = db.insert("CLASS", null, values)
        return true
    }

    fun insertSkill(skill: Skill, db: SQLiteDatabase = writableDatabase): Boolean {

        val values = ContentValues()
        values.put("id", skill.id)
        values.put("name", skill.name)
        values.put("description", skill.description)
        values.put("level", skill.levelRequired)
        values.put("class", skill.classRequired)


        values.put("races", skill.raceRequired.joinToString(","))
        val bos = ByteArrayOutputStream()
        skill.image.compress(Bitmap.CompressFormat.PNG, 100, bos)

        values.put("image", bos.toByteArray())

        val newRowId = db.insert("SKILL", null, values)
        return true
    }

    fun getSkillyId(id: Int): Skill {
        val db = writableDatabase

        var cursor: Cursor? = null
        try {

            cursor = db.rawQuery(
                "SELECT * FROM SKILL WHERE SKILL.ID = ?", arrayOf(id.toString())
            )
        } catch (e: SQLiteException) {
            return Skill(
                0,
                "",
                "",
                0,
                BitmapFactory.decodeResource(context.resources, R.drawable.fireball),
                "",
                arrayListOf()
            )
        }
        cursor.moveToFirst()
        val skillId = cursor.getInt(cursor.getColumnIndex("id"))
        val description = cursor.getString(cursor.getColumnIndex("description"))
        val name = cursor.getString(cursor.getColumnIndex("name"))
        val classRequired = cursor.getString(cursor.getColumnIndex("class"))
        val raceRequired = cursor.getString(cursor.getColumnIndex("races")).split(",")
        val levelRequired = cursor.getInt(cursor.getColumnIndex("level"))


        val imageArray = cursor.getBlob(cursor.getColumnIndex("image"))
        val image = BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size)

        val skill = Skill(
            skillId, name, description, levelRequired, image, classRequired,
            raceRequired as ArrayList<String>
        )
        cursor.close()

        return skill
    }

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "WoWSkills.db"

        private const val SQL_CREATE_CLASS_ENTRIES =
            "CREATE TABLE CLASS(" +
                    "id INT PRIMARY KEY," +
                    "name TEXT," +
                    "description TEXT," +
                    "image BLOB)"

        private const val SQL_CREATE_RACE_ENTRIES =
            "CREATE TABLE RACES(" +
                    "id INT PRIMARY KEY," +
                    "name TEST," +
                    "description TEXT," +
                    "image BLOB)"
        private const val SQL_CREATE_SKILL_ENTRIES =
            "CREATE TABLE SKILL(" +
                    "id INT PRIMARY KEY," +
                    "name TEXT," +
                    "description TEXT," +
                    "level INT," +
                    "class TEXT," +
                    "races TEXT," +
                    "image BLOB)"

        private const val SQL_DROP_RACE_TABLE =
            "DROP TABLE IF EXISTS RACES"

        private const val SQL_DROP_CLASS_TABLE =
            "DROP TABLE if EXISTS CLASS"
        private const val SQL_DROP_SKILL_TABLE =
            "DROP TABLE if EXISTS SKILL"

    }

}