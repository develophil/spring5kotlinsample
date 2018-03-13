package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.util.function.Tuple2
import java.time.Duration
import java.util.*
import java.util.stream.Stream

@Component
class ReactiveHandler {

    fun test() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()

        val a: Flux<String> = Flux.fromArray(arrayOf("a","b"))


        return Flux.zip( interval, a ).map{it.t2}
    }

    fun findTest(): String {
        val random = Random()
        return "test" + random.nextInt()
    }
}