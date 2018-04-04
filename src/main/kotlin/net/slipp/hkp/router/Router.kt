package net.slipp.hkp.router

import net.slipp.hkp.handler.HelloWorldHandler
import net.slipp.hkp.handler.RacingHandler
import net.slipp.hkp.handler.ViewHandler
import net.slipp.hkp.racing.Car
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class Router {
    @Bean
    fun routeFunction(handler: HelloWorldHandler) : RouterFunction<ServerResponse> = router {
        ("/").nest {
            GET("/helloworld") { req ->
                ServerResponse.ok().body(
                        handler.helloworld()
                )
            }
            POST("/router-car-reactive") { req ->
                ServerResponse.ok().body(
                        handler.saveCarFlux(Car("aaa"))
                )
            }
            GET("/router-car-reactive") { req ->
                ServerResponse.ok().body(
                        handler.getCarFlux()
                )
            }
            GET("/router-car-reactive-paged") { req ->
                ServerResponse.ok().body(
                        handler.getCarFlux(req.queryParam("page").get().toInt(), req.queryParam("size").get().toInt())
                )
            }

        }
    }

    @Bean
    fun racingRouter(
            racingHandler: RacingHandler, viewHandler: ViewHandler) = router {

        ("/racing").nest {
            GET("") { viewHandler.getView("index") }
            GET("/index") { racingHandler.index() }
            GET("/game") { viewHandler.getView("game") }
            GET("/result") { viewHandler.getView("result") }

            POST("/game/create", racingHandler::joinGame)
            POST("/game/start", racingHandler::playGame)

            accept(TEXT_EVENT_STREAM).nest {
                GET("/game/react", racingHandler::reactGame)
                GET("/game/replay", racingHandler::replay)
            }
        }
    }
}
