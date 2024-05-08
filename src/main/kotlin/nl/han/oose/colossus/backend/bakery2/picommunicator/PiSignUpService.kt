package nl.han.oose.colossus.backend.bakery2.picommunicator

interface PiSignUpService {

    fun createSignUpRequest(macAddress: String)

    fun setPiSignUpDao(piSignUpDao: PiSignUpDao)
}

