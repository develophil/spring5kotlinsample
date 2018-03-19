package net.slipp.hkp.web

import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@RestController
class HelloWorldController() {
    @GetMapping("/default")
    fun hello(): String {
        return "Hello World!"
    }

    @GetMapping("/mono")
    fun helloMono(): Mono<String> {
        return Mono.just("Hello world!!!!")
    }

    @PostMapping("/form")
    fun helloMono(@ModelAttribute("test") test: TestCls,  model: Model): Mono<String> {
        return Mono.just(model.toString())
    }

    @GetMapping(produces = arrayOf(MediaType.APPLICATION_STREAM_JSON_VALUE), value = "flux")
    fun fluxTest(): Flux<Any> {
        return test()
    }


    fun test() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()

        val a: Flux<String> = Flux.fromArray(arrayOf("a","b"))


        return Flux.zip( interval, a ).map{it.t2}
    }

}


data class TestCls(val test: Integer)