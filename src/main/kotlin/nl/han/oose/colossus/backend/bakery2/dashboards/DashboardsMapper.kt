package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import java.sql.ResultSet

interface DashboardsMapper {

    fun getAlldashboardsMapper(resultSet: ResultSet): DashboardCollectionDto
    fun getUserIdMapper(resultSet: ResultSet): Int?
}