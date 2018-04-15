package racing

import net.slipp.hkp.racing.Car

data class RacingGame(
        var racingRound: Int,
        var gameStatus: GameStatus,
        var cars: MutableSet<Car>,
        var raceList: MutableSet<Race>,
        var replayList: MutableList<MutableSet<Race>>,
        var totalTurns: Int,
        var currentTurn: Int,
        var announcement: String) {

    constructor() : this (
        1, GameStatus.READY, mutableSetOf(), mutableSetOf(), mutableListOf(), 10, 0, "레이싱 경기가 곧 시작됩니다."
    )

    var winners: List<Race> = arrayListOf()

    fun isReady(): Boolean = (gameStatus == GameStatus.READY)
    fun getRemainTurns(): Int = totalTurns - currentTurn

    fun joinCar(names: List<String>) {
        names.forEach {
            cars.add(Car(it))
        }
    }

    fun addCarToRaceList() = cars.forEach { raceList.add(Race(it, 0)) }

    fun startRace(laps: Int) {
        totalTurns = laps
        gameStatus = GameStatus.RACING
        announcement = "경기가 시작되었습니다."
    }

    fun playGame(laps: Int) {

        addCarToRaceList()  // 중간에 합류하도록 무조건 추가.

        if (gameStatus == GameStatus.READY) {
            startRace(laps)
            play()
        }
    }

    fun addCurrentTurn() {
        currentTurn += 1
    }

    fun play() {
//        thread(start = true) {

            for (i in 1..totalTurns) {

                Thread.sleep(1000)
                addCurrentTurn()

                var msr = mutableSetOf<Race>()

                for (race in raceList) {
                    race.forward()
                    msr.add(race.copy())
                }

                replayList.add(msr)


            }
            endRace()
            checkWinners()
            announceWinners()
//        }
    }

    fun endRace() {
        gameStatus = GameStatus.END
        announcement = "경기가 종료되었습니다."
    }
    fun announceWinners() {
        // 반환된 race 리스트에서 차 이름만 ', ' 로 join 하여 출력함.
        announcement = "우승자는 " + winners.joinToString {it.car.name} + "입니다!!!"
    }

    fun checkWinners() {
        // raceList 중 가장 큰 distance를 키로 가지는 race 그룹을 반환
        winners = raceList.groupBy { it.distance }.maxBy { it.key }!!.value
    }




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

    enum class GameStatus {
        READY, RACING, END

    }

}

