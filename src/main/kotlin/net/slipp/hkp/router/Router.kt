package net.slipp.hkp.router

import net.slipp.hkp.handler.HelloWorldHandler
import net.slipp.hkp.handler.RacingHandler
import net.slipp.hkp.handler.ReactiveHandler
import net.slipp.hkp.handler.ViewHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.TEXT_EVENT_STREAM
import org.springframework.web.reactive.function.server.*

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
        }
    }

    @Bean
    fun reactiveFunction(handler: ReactiveHandler) : RouterFunction<ServerResponse> = router {
        ("/").nest {
            GET("/reactive"){ req ->
                ServerResponse.ok().bodyToServerSentEvents(
                        handler.test()
                )
            }
        }
    }

    @Bean
    fun racingRouter(
            racingHandler: RacingHandler, viewHandler: ViewHandler) = router {

        ("/racing").nest {
            GET("") { viewHandler.getView("index") }
            GET("/game") { viewHandler.getView("game") }
            GET("/result") { viewHandler.getView("result") }

//            POST("/game/create") { req -> racingHandler.createGame(req) }
//            GET("/game/ready", racingHandler::readyGame)
            POST("/game/create", racingHandler::joinGame)
            POST("/game/start", racingHandler::playGame)

            accept(TEXT_EVENT_STREAM).nest {
                GET("/game/ready", racingHandler::readyGame)
                GET("/game/race", racingHandler::startRace)
            }
        }


    }
}
