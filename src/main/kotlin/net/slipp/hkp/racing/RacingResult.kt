package net.slipp.hkp.racing

data class RacingResult(
    val round: Int,
    val winners: List<Race>
) {
    constructor() : this ( 0, arrayListOf() )
}