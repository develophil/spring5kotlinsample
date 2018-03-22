package net.slipp.hkp.racing

data class Car(var name: String) {

    override fun equals(other: Any?): Boolean {
        return name.equals((other as Car).name)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}