package net.slipp.hkp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebfluxcarApplication

fun main(args: Array<String>) {
    runApplication<WebfluxcarApplication>(*args)
}
