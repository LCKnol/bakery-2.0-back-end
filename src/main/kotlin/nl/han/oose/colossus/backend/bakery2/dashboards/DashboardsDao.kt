package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto

interface DashboardsDao {

    fun getAllDashboards(): DashboardCollectionDto
    fun getDashboard(dashboardId: Int): DashboardDto?
    fun deleteDashboard(dashboardId: Int)
    fun getUserIdFromDashboard(dashboardId: Int): Int?
    fun setDashboardsMapper(mapper: DashboardsMapper)
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun addDashboard(dashboardDto: DashboardDto)
    fun editDashboard(dashboardDto: DashboardDto)

}