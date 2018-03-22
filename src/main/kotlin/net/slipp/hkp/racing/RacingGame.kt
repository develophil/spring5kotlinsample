package racing

import net.slipp.hkp.racing.Car
import kotlin.concurrent.thread

data class RacingGame(
        var gameStatus: GameStatus,
        var cars: MutableSet<Car>,
        var raceList: MutableSet<Race>,
        var totalTurns: Int,
        var currentTurn: Int,
        var announcement: String) {

    constructor() : this (
        GameStatus.READY, mutableSetOf(), mutableSetOf(), 10, 0, "레이싱 경기가 곧 시작됩니다."
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
            announcement = "경기가 시작되었습니다."
            play()
        }
        makeRaceList()
    }

    fun turn() {
        currentTurn += 1
    }

    fun canForward(): Boolean {
        return when {
            ( Math.random() * 10 ) >= 4 -> true
            else -> false
        }
    }

    fun forward(race: Race) {
        if(canForward())
            race.distance += 1
    }

    fun play() {
        thread(start = true) {
            for (i in 1..totalTurns) {
                Thread.sleep(1000)
                turn()
                for (race in raceList) {
                    forward(race)
                }
            }
            endRace()
            announceWinners()
        }
    }

    fun endRace() {
        gameStatus = GameStatus.END
        announcement = "경기가 종료되었습니다."
    }
    fun announceWinners() {
        val winners = makeWinners().joinToString {
            it.car.name
        }
        announcement = "우승자는 " + winners +"입니다!!!"
    }

    fun makeWinners(): List<Race> {
        val winners = raceList.groupBy { it.distance }.maxBy { it.key }
        println("winners : "+winners)
        println("winners : "+winners!!.value)
        return winners!!.value
    }

    data class Race(val car: Car, var distance: Int) {
        fun forward(): Int  = 0

        override fun equals(other: Any?): Boolean {
            return car.equals((other as Race).car)
        }

        override fun hashCode(): Int {
            return car.hashCode()
        }
    }

    enum class GameStatus {
        READY, RACING, END

    }

    fun init() {
        gameStatus = GameStatus.READY
    }

}

