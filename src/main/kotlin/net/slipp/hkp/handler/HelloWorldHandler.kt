package net.slipp.hkp.handler

import net.slipp.hkp.racing.Car
import net.slipp.hkp.repository.ResultReactiveRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class HelloWorldHandler(private val resultReactiveRepository: ResultReactiveRepository) {

    private val DELAY_PER_ITEM_MS = 100

    fun helloworld() : Mono<String> {
        return Mono.just("Hello World handler!")
    }

    fun saveCarFlux(car: Car): Mono<Car> {
        return resultReactiveRepository.save(car)
    }

    fun getCarFlux(): Flux<Car> {
        return resultReactiveRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

    fun getCarFlux(page: Int,size: Int): Flux<Car> {
        return resultReactiveRepository.retrieveAllCarPaged(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

}