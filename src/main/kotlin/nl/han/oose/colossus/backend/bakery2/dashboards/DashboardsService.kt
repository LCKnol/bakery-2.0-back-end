package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto

interface DashboardsService {

    fun getAllDashboards(): DashboardCollectionDto
    fun setDashboardDao(dao: DashboardsDao)
}