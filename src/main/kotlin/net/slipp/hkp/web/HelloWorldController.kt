package net.slipp.hkp.web

import net.slipp.hkp.racing.Car
import net.slipp.hkp.repository.ResultReactiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.RequestParam



@RestController
class HelloWorldController(private val resultReactiveRepository: ResultReactiveRepository) {

    private val DELAY_PER_ITEM_MS = 100

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

    @PostMapping("/car-reactive")
    fun saveCarFlux(): Mono<Car> {
        return resultReactiveRepository.save(Car("test!"))
    }

    @GetMapping("/car-reactive")
    fun getCarFlux(): Flux<Car> {
        return resultReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

    @GetMapping("/car-reactive-paged")
    fun getCarFlux(@RequestParam(name = "page") page: Int,
                     @RequestParam(name = "size") size: Int): Flux<Car> {
        return resultReactiveRepository.retrieveAllCarPaged(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }
}


data class TestCls(val test: Integer)