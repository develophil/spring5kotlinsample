package net.slipp.hkp.racing

data class Race(val car: Car, var distance: Int) {

    fun canForward(): Boolean {
        return when {
            ( Math.random() * 10 ) >= 4 -> true
            else -> false
        }
    }

    fun forward() {
        if (canForward()) {
            distance += 1
        }
    }

    override fun equals(other: Any?): Boolean {
        return car.equals((other as Race).car)
    }

    override fun hashCode(): Int {
        return car.hashCode()
    }
}
