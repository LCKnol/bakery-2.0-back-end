package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto

interface DashboardsService {

    fun getAllDashboards(): DashboardsDto
}