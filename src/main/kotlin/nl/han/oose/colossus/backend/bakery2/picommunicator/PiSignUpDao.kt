package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto

interface PiSignUpDao {
    fun insertSignUpRequest(macAddress: String, ipAddress: String)
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun checkPiExists(macAddress: String): Boolean
    fun checkPiSignUpExists(macAddress: String): Boolean
}