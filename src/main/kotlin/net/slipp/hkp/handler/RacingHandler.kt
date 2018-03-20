package net.slipp.hkp.handler

import net.slipp.hkp.racing.Car
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import racing.RacingGame
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.time.Duration.ofMillis

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


    fun test() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()
        val a: Flux<String> = Flux.fromArray(arrayOf("a","b"))
        return Flux.zip( interval, a ).map{it.t2}
    }

    private val gameStream = Flux.just(racingGame)
//            .zip(Flux.interval(ofMillis(500)), Flux.just(racingGame))
//            .map { it.t2 }

    fun readyGame(req: ServerRequest): Mono<ServerResponse> {
        req.body(BodyExtractors.toFormData())
                .log()
                .map(MultiValueMap<String, String>::toSingleValueMap)

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).bodyToServerSentEvents(gameStream)
    }


    private val raceStream = Flux
            .zip(Flux.interval(ofMillis(500)), Flux.just(racingGame))
            .map { it.t2 }

    fun startRace(req: ServerRequest): Mono<ServerResponse> {
        req.body(BodyExtractors.toFormData())
                .log()
                .map(MultiValueMap<String, String>::toSingleValueMap)

                racingGame.play()

        return ServerResponse.ok().bodyToServerSentEvents(raceStream)
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
