package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.ui.Model
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class RacingHandler {

    val map : HashMap<String, String> = HashMap()

    fun createGame(req: ServerRequest) =
            req.body(BodyExtractors.toFormData())
            .flatMap {
                val formData = it.toSingleValueMap()
                println("names : "+formData["names"])
                map.put("names", formData["names"]!!)
                Mono.just(formData)
            }
            .then(goPageWithData("game", map))

    fun playGame(req: ServerRequest) =
        goPage("result")

    fun goPage(pageName: String): Mono<ServerResponse> =
        ok().render(pageName)

    fun goPageWithData(pageName: String, model: Map<String, String>): Mono<ServerResponse> =
        ok().render(pageName, model)
}
