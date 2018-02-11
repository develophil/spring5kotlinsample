package net.slipp.hkp.router

import net.slipp.hkp.handler.HelloWorldHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfiguration {
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
    fun racingRouter() : RouterFunction<ServerResponse> = router {
        accept(MediaType.TEXT_HTML).nest {
            GET("/racing") { ServerResponse.ok().render("index") }
            GET("/racing/game") { ServerResponse.ok().render("game") }
            GET("/racing/result") { ServerResponse.ok().render("result") }
        }
    }
}