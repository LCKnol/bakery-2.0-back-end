package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto

interface DashboardsDao {

    fun getAllDashboards(): DashboardCollectionDto
    fun setDashboardsMapper(mapper: DashboardsMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
}