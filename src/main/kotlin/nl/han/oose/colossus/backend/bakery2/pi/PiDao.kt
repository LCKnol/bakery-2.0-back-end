package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto

interface PiDao {
    fun getPisFromUser(user: Int): PiCollectionDto
    fun setPiMapper(mapper: PiMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun removeDashboardFromPis(dashboardId: Int)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun insertPi(macAddress: String, ipAddress: String, name: String, roomno: String)
    fun deletePiRequest(macAddress: String)
    fun editPi(piDto: PiDto)
    fun updatePiIp(piSignUpRequestDto: PiSignUpRequestDto)
    fun getPi(piId: Int): PiDto?
    fun getMacAddress(piId: Int):String
    fun assignDashboard(piId: Int,dashboardId: Int)
    fun getPi(piId: Int?, macAddress: String?): PiDto?
    fun updateStatus(status: String, piId: Int)
}