package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO

interface PiService {

    fun getPis(user: Int):PiCollectionDTO
}