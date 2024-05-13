package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto

interface PiService {
    fun getPis(user: Int): PiCollectionDto
    fun setPiDao(dao: PiDao)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun addPi(macAddress: String, name: String, roomno: String)
    fun declinePiRequest(macAddress: String)
    fun handlePiRequest(macAddress: String, isAccepted: Boolean)
}