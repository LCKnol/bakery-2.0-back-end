package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto

interface PiService {
    fun getPis(user: Int): PiCollectionDto
    fun setPiDao(dao: PiDao)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun editPi(piDto: PiDto, userId: Int)
    fun getPi(piId: Int):PiDto?
}