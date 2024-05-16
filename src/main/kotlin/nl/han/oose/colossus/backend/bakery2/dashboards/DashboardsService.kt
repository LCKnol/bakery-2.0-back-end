package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto

interface DashboardsService {

    fun getAllDashboards(userId: Int): DashboardCollectionDto
    fun setDashboardDao(dao: DashboardsDao)
    fun setPiDao(dao: PiDao)
    fun deleteDashboard(dashboardId: Int)
    fun addDashboard(dashboardDto: DashboardDto)
    fun editDashboard(dashboardDto: DashboardDto)
    fun getDashboard(dashboardId: Int, userId: Int): DashboardDto
}