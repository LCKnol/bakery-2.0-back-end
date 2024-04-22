package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto

interface PiDao {
    fun getPis(user: Int): PiCollectionDto
    fun setPiMapper(mapper: PiMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setDashboardsNull(dashboardId: Int)

}