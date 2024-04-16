package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto

interface DashboardsDao {

    fun getAllDahsboards(): DashboardsDto
}