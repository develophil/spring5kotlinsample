package racing

import net.slipp.hkp.racing.Car

data class RacingGame(var gameStatus: GameStatus, var cars: MutableSet<Car>, var raceList: MutableList<Race>, var winners: MutableSet<Car>, var totalTurns: Int, var currentTurn: Int) {

    constructor() : this (
        GameStatus.READY, mutableSetOf(), mutableListOf(), mutableSetOf(), 10, 0
    )

    fun isReady(): Boolean = (gameStatus == GameStatus.READY)
    fun getRemainTurns(): Int = totalTurns - currentTurn

    fun joinCar(names: List<String>) {
        names.forEach {
            println("car name : "+ it)
            cars.add(Car(it))
        }
    }

    fun makeRaceList() = cars.forEach { raceList.add(Race(it, 0)) }

    fun startRace(laps: Int) {
        totalTurns = laps
        gameStatus = GameStatus.RACING
        makeRaceList()
    }

    fun endRace() {
        gameStatus = GameStatus.READY
    }
    fun announceWinners() {}

    data class Race(val car: Car, var distance: Int) {
        fun forward(): Int  = 0
    }

    enum class GameStatus {
        READY, RACING

    }

}

