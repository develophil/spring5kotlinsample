package net.slipp.hkp.router

import net.slipp.hkp.handler.HelloWorldHandler
import net.slipp.hkp.handler.RacingHandler
import net.slipp.hkp.handler.ViewHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun racingRouter(
            racingHandler: RacingHandler, viewHandler: ViewHandler) = router {

        ("/racing").nest {
            GET("") { viewHandler.getView("index") }
            GET("/game") { viewHandler.getView("game") }
            GET("/result") { viewHandler.getView("result") }

            POST("/game/create", racingHandler::createGame)
            POST("/game/start", racingHandler::playGame)


        }


    }
}