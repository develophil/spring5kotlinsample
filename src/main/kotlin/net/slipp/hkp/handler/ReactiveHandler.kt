package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*

@Component
class ReactiveHandler {

    fun test() : Flux<Any> {

        val interval = Flux.interval(Duration.ofMillis(1000))
        interval.subscribe()

        val a: Flux<String> = Flux.fromArray(arrayOf("a","b"))


        return Flux.zip( interval, a ).map{it.t2}

//        val arr: Array<String> = arrayOf("a","b","c","d","e","f")
//        return Flux.interval(Duration.ofMillis(500)).take(arr.size.toLong()).map { make() }
    }


    fun make(): String {
        return "test : " + Random().nextInt()
    }
}