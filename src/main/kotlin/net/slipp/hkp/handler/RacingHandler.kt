package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import org.springframework.ui.Model
import reactor.core.publisher.Mono

@Component
class RacingHandler {
    fun startGame(model: Model) : String {
        return "game"
    }
}