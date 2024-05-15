package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto

interface PiService {
    fun getPis(user: Int): PiCollectionDto
    fun setPiDao(dao: PiDao)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun editPi(piDto: PiDto,userId: Int)
    fun updatePiIp(piSignUpRequestDto: PiSignUpRequestDto)
    fun getPi(piId: Int):PiDto
    fun addPi(macAddress: String, ipAddress: String, name: String, roomno: String)
    fun declinePiRequest(macAddress: String)
    fun handlePiRequest(macAddress: String, isAccepted: Boolean)
}