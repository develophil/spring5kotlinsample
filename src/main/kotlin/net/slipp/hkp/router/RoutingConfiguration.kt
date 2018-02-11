package net.slipp.hkp.router

import net.slipp.hkp.handler.HelloWorldHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class RoutingConfiguration {
    @Bean
    fun routeFunction(handler: HelloWorldHandler) : RouterFunction<ServerResponse> = router {
        ("/").nest {
            GET("/route") { req ->
                ServerResponse.ok().body(
                        handler.helloworld()
                )
            }
        }
    }
}