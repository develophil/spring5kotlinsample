package net.slipp.hkp.handler

import net.slipp.hkp.racing.Car
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import racing.RacingGame
import reactor.core.publisher.Mono

@Component
class RacingHandler {

    var racingGame: RacingGame = RacingGame()

    fun joinGame(req: ServerRequest): Mono<ServerResponse> =
        req.body(BodyExtractors.toFormData())
        .log()
        .flatMap {
            val formData = it.toSingleValueMap()
            val names = formData["names"]!!.toString().split(" ")
            println("names : " + names)
            racingGame.joinCar(names)
            goPageWithObject("game", racingGame)
        }

    fun createGame(req: ServerRequest): Mono<ServerResponse>  =
            req.body(BodyExtractors.toFormData())
                    .log()
                    .map(MultiValueMap<String, String>::toSingleValueMap)
                    .flatMap {
                        goPageWithData("game", createDataMap(it))
                    }

    fun playGame(req: ServerRequest): Mono<ServerResponse> =
            req.body(BodyExtractors.toFormData())
                    .log()
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        val laps = formData["turn"]!!.toInt()
                        println("laps : " + laps)
                        racingGame.startRace(laps)
                        goPageWithObject("result", racingGame)
                    }

    fun goPage(pageName: String): Mono<ServerResponse> =
            ok().render(pageName)

    fun goPageWithObject(pageName: String, model: Any): Mono<ServerResponse> =
            ok().render(pageName, model)

    fun goPageWithData(pageName: String, model: Map<String, Any>): Mono<ServerResponse> =
            ok().render(pageName, model)

    fun createDataMap(map: Map<String, String>): HashMap<String, Any> {

        var dataMap : HashMap<String, Any> = HashMap()
        var cars: ArrayList<Car> = arrayListOf()

        for (s in map["names"]!!.split(" ")!!) {
            println(s)
            cars.add(Car(s))
        }

        dataMap.put("players", cars)

        return dataMap

    }
}
