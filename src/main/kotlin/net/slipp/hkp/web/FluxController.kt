package net.slipp.hkp.web

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Controller
class FluxController() {

    @GetMapping(value = "/test/reactive")
    fun fluxTest(model: Model): String {

        model.addAttribute("test", test())

        return "test"
    }

    @GetMapping(value = "/test/page")
    fun pageTest(model: Model): String {

        model.addAttribute("test", "test!!!!!")

        return "test"
    }


    fun test() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()

        val a: Flux<String> = Flux.fromArray(arrayOf("a","b"))


        return Flux.zip( interval, a ).map{it.t2}
    }

}