package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto

interface PiDao {
    fun getPis(user: Int): PiCollectionDto

}