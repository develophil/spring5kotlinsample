package net.slipp.hkp.web

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
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

    @PostMapping("/form")
    fun helloMono(@ModelAttribute("test") test: TestCls,  model: Model): Mono<String> {
        return Mono.just(model.toString())
    }
}


data class TestCls(val test: Integer)