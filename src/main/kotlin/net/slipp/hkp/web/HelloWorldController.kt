package net.slipp.hkp.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloWorldController {
    @GetMapping("/default")
    fun hello(): String {
        return "Hello World!"
    }


    @GetMapping("/mono")
    fun helloMono(): Mono<String> {
        return Mono.just("Hello world!!!!")
    }
}