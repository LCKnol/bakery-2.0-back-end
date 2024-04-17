package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto

interface DashboardsDao {

    fun getAllDashboards(): DashboardCollectionDto
}