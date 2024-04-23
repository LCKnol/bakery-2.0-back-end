package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto

interface DashboardsService {

    fun getAllDashboards(): DashboardCollectionDto
    fun setDashboardDao(dao: DashboardsDao)
    fun setPiDao(dao: PiDao)
    fun deleteDashboard(dashboardId: Int, userId: Int)
    fun addDashboard(dashboardDto: DashboardDto)
    fun editDashboard(dashboardDto: DashboardDto, userId: Int)
    fun getDashboard(dashboardId: Int): DashboardDto
}