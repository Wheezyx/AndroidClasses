package pl.wedel.lab4

import java.io.Serializable


enum class Race(val description: String, val image: Int) {
    HUMAN("Powerful as mage", R.drawable.human_race), ORC("VERY STRONG", R.drawable.orc_race), ELF(
        "Nice ears!",
        R.drawable.elf_race
    ),
    UNDEAD("They are dead!", R.drawable.undead_race)
}

enum class Class(val description: String, val image: Int) {
    MAGE("Attacks by wand", R.drawable.mage), WARRIOR(
        "Using swords",
        R.drawable.warrior
    ),
    ROGUE("Using bow and daggers", R.drawable.rogue)
}

data class Skill(
    val name: String,
    val description: String,
    val levelRequired: Int,
    val image: Int,
    val classRequired: Class,
    val raceRequired: Array<Race>
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Skill

        if (name != other.name) return false
        if (description != other.description) return false
        if (levelRequired != other.levelRequired) return false
        if (classRequired != other.classRequired) return false
        if (!raceRequired.contentEquals(other.raceRequired)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + levelRequired
        result = 31 * result + classRequired.hashCode()
        result = 31 * result + raceRequired.contentHashCode()
        return result
    }
}