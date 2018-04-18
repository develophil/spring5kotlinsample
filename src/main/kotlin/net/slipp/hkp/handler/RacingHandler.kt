package net.slipp.hkp.handler

import net.slipp.hkp.racing.Race
import net.slipp.hkp.racing.RacingResult
import net.slipp.hkp.repository.ResultReactiveRepository
import org.springframework.http.codec.ServerSentEvent
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyToServerSentEvents
import racing.RacingGame
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.concurrent.thread

@Component
class RacingHandler(val resultReactiveRepository: ResultReactiveRepository) {

    var racingGame: RacingGame = RacingGame()

    fun findLastRacingResult(): RacingResult {
        return resultReactiveRepository.findAll().last(RacingResult()).block()
    }

    fun index(): Mono<ServerResponse> {
        if (racingGame.gameStatus == RacingGame.GameStatus.END) {
            racingGame = RacingGame ()
            println ("racingGame : " + racingGame)
        }

        val results:RacingResult = findLastRacingResult()
        val racingRound: Int = results.round + 1
        racingGame.racingRound = racingRound

        return goPageWithObject("index", racingRound)
    }

    fun joinGame(req: ServerRequest): Mono<ServerResponse> =
        req.body(BodyExtractors.toFormData())
        .log()
        .flatMap {
            val formData = it.toSingleValueMap()
            val names = formData["names"]!!.toString().split(" ")
            println("names : " + names)
            racingGame.joinCar(names)
            println("racingGame : "+racingGame)
            goPageWithObject("game", racingGame)
        }

    fun makeGameStream() : Flux<Any> {
        return Flux.just(ServerSentEvent.builder(racingGame).id("testId").event("message").retry(Duration.ofSeconds(1)).build())
    }

    fun makeReplayStream() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()
        val a = Flux.fromArray(racingGame.replayList.toTypedArray())

        return Flux.zip( interval, a ).map{
            ServerSentEvent.builder(it.t2).id("test").event("message").retry(Duration.ofSeconds(5)).build()
        }
    }

    fun reactGame(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().bodyToServerSentEvents(makeGameStream()).log()
    fun replay(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().bodyToServerSentEvents(makeReplayStream()).log()
    fun gameHistory(req: ServerRequest): Mono<ServerResponse> = ServerResponse.ok().bodyToServerSentEvents(resultReactiveRepository.findAll().delayElements(Duration.ofMillis(500))).log()

    fun playGame(req: ServerRequest): Mono<ServerResponse> =
            req.body(BodyExtractors.toFormData())
                    .log()
                    .flatMap {
                        val formData = it.toSingleValueMap()
                        val laps = formData["turn"]!!.toInt()
                        thread(start = true) {
                            racingGame.playGame(laps)
                            saveResult(racingGame.racingRound, racingGame.winners)
                        }
                        goPageWithObject("result", racingGame)
                    }

    fun saveResult(racingRound: Int, winners: List<Race>) {

        println("racingRound!! : " + racingRound)
        println("saveresult!! : " + winners)

        resultReactiveRepository.save(RacingResult(racingRound, winners)).block()
    }

    fun goPage(pageName: String): Mono<ServerResponse> =
            ok().render(pageName)

    fun goPageWithObject(pageName: String, model: Any): Mono<ServerResponse> =
            ok().render(pageName, model)

}
