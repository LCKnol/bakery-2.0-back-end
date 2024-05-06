package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection

interface PiSignUpDao {
    fun insertSignUpRequest(macAddress: String)

    fun setDatabaseConnection(connection: DatabaseConnection)
}