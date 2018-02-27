package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class RacingHandler {

    fun createGame(req: ServerRequest): Mono<ServerResponse>  =
        req.body(BodyExtractors.toFormData())
        .log()
        .map(MultiValueMap<String, String>::toSingleValueMap)
        .flatMap {
            goPageWithData("game", createDataMap(it))
        }

    fun playGame(req: ServerRequest): Mono<ServerResponse> =
        goPage("result")

    fun goPage(pageName: String): Mono<ServerResponse> =
        ok().render(pageName)

    fun goPageWithData(pageName: String, model: Map<String, Any>): Mono<ServerResponse> =
        ok().render(pageName, model)

    fun createDataMap(map: Map<String, String>): HashMap<String, Any> {

        var dataMap : HashMap<String, Any> = HashMap()
        var players: ArrayList<Player> = arrayListOf()

        for (s in map["names"]!!.split(" ")!!) {
            println(s)
            players.add(Player(s))
        }

        dataMap.put("players", players)

        return dataMap

    }
}

data class Player(var name: String)