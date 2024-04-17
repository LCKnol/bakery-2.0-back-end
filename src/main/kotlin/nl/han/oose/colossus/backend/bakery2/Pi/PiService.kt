package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto

interface PiService {

    fun getPis(user: Int):PiCollectionDto
}