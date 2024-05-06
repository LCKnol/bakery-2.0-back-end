package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto

interface PiDao {
    fun getPis(user: Int): PiCollectionDto
    fun setPiMapper(mapper: PiMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun removeDashboardFromPis(dashboardId: Int)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun editPi(piDto: PiDto)
    fun getPi(piId: Int): PiDto?
}