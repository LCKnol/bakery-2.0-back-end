package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto

interface PiSignUpService {

    fun createSignUpRequest(macAddress: String)

    fun setPiSignUpDao(piSignUpDao: PiSignUpDao)
    fun checkPiExists(request: PiSignUpRequestDto) :Boolean

    fun checkPiSignUpExists(request: PiSignUpRequestDto) : Boolean
}

