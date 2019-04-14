package pl.wedel.lab4

import android.graphics.Bitmap
import java.io.Serializable


data class Race(val id: Int, val name: String, val description: String, val image: Bitmap) : Serializable

data class Class(val id: Int, val name: String, val description: String, val image: Bitmap) : Serializable

data class Skill(
    val id: Int,
    val name: String,
    val description: String,
    val levelRequired: Int,
    val image: Bitmap,
    val classRequired: String,
    val raceRequired: ArrayList<String>
) : Serializable