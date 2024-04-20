package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto

interface DashboardsService {

    fun getAllDashboards(): DashboardCollectionDto
    fun setDashboardDao(dao: DashboardsDao)
    fun addDashboard(dashboardDto: DashboardDto)
}