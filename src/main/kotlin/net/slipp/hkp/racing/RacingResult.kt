package net.slipp.hkp.racing

import racing.RacingGame

data class RacingResult(
    val round: Int,
    val winners: List<RacingGame.Race>
) {
    constructor() : this ( 0, arrayListOf() )
}