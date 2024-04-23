package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import java.sql.ResultSet

interface DashboardsMapper {

    fun getAllDashboardsMapper(resultSet: ResultSet): DashboardCollectionDto
    fun getDashboardMapper(resultSet: ResultSet): DashboardDto?
    fun getUserIdMapper(resultSet: ResultSet): Int?
}