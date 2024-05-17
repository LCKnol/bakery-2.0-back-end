package nl.han.oose.colossus.backend.bakery2.picommunicator

interface PiSignUpService {
    fun createSignUpRequest(macAddress: String, ipAddress: String)
    fun setPiSignUpDao(piSignUpDao: PiSignUpDao)
    fun checkPiExists(macAddress: String): Boolean
    fun checkPiSignUpExists(macAddress: String): Boolean
}