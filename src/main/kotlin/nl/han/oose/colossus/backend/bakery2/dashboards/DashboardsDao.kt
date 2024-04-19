package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto

interface DashboardsDao {

    fun getAllDashboards(): DashboardCollectionDto
    fun deleteDashboard(dashboardId: Int)
    fun userOwnsDashboard(dashboardId: Int, userId: Int): Boolean
    fun setDashboardsMapper(mapper: DashboardsMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
}