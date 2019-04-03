package pl.wedel.lab2


class AcitivityUtils {
    companion object {
        fun getIntent(id: Int) = when (id) {
            R.id.go_to_third -> ThirdActivity::class.java
            R.id.go_to_second -> SecondActivity::class.java
            R.id.go_to_main -> MainActivity::class.java
            else -> throw NotImplementedError()
        }
    }
}