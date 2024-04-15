package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO

interface PiDao {
    fun getPis(user: Int): PiCollectionDTO

}