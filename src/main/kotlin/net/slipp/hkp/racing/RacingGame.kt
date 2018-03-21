package racing

import net.slipp.hkp.racing.Car

data class RacingGame(
        var gameStatus: GameStatus,
        var cars: MutableSet<Car>,
        var raceList: MutableList<Race>,
        var winners: MutableSet<Car>,
        var totalTurns: Int,
        var currentTurn: Int,
        var announcement: String) {

    constructor() : this (
        GameStatus.READY, mutableSetOf(), mutableListOf(), mutableSetOf(), 10, 0, "레이싱 경기가 곧 시작됩니다."
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

        if (gameStatus == GameStatus.READY) {
            totalTurns = laps
            gameStatus = GameStatus.RACING
            makeRaceList()
            announcement = "경가 시작되었습니다."
        }

    }

    fun turn() {
        currentTurn += 1
    }

    fun getForwardDistance(): Boolean {
        return when {
            ( Math.random() * 10 ) >= 4 -> true
            else -> false
        }
    }

    fun forward(race: Race) {
        if(getForwardDistance())
            race.distance += 1
    }

    fun play() {
        for (i in 1..totalTurns) {
            turn()
            for (race in raceList) {
                forward(race)
            }
        }
        endRace()
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

