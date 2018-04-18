package net.slipp.hkp.handler

import net.slipp.hkp.racing.Car
import net.slipp.hkp.repository.CarReactiveTestRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class HelloWorldHandler(private val carReactiveTestRepository: CarReactiveTestRepository) {

    private val DELAY_PER_ITEM_MS = 1000

    fun helloworld() : Mono<String> {
        return Mono.just("Hello World handler!")
    }

    fun saveCarFlux(car: Car): Mono<Car> {
        return carReactiveTestRepository.save(car)
    }

    fun getCarFlux(): Flux<Car> {
        return carReactiveTestRepository.findAll().delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

    fun getCarFlux(page: Int,size: Int): Flux<Car> {
        return carReactiveTestRepository.retrieveAllCarPaged(PageRequest.of(page, size))
                .delayElements(Duration.ofMillis(DELAY_PER_ITEM_MS.toLong()))
    }

}