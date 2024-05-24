package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
@Primary
class DashboardMapperImp : DashboardsMapper {

    override fun getAllDashboardsMapper(resultSet: ResultSet): DashboardCollectionDto {
        val newDashboardCollectionDto = DashboardCollectionDto()
        while (resultSet.next()) {
            val newDashboardDto = this.mapDashboard(resultSet)
            newDashboardCollectionDto.getDashboards().add(newDashboardDto)
        }
        return newDashboardCollectionDto
    }

    override fun getDashboardMapper(resultSet: ResultSet): DashboardDto? {

        var dashboard: DashboardDto? = null

        while (resultSet.next()) {
            dashboard = this.mapDashboard(resultSet)
        }
        return dashboard
    }

    override fun getTeamIdMapper(resultSet: ResultSet): Int? {
        var userId: Int? = null
        while (resultSet.next()) {
            userId = resultSet.getInt("TEAMID")
        }
        return userId
    }

    private fun mapDashboard(dashboard: ResultSet): DashboardDto {
        val team = TeamDto()
        team.setName(dashboard.getString("teamName"))
        team.setId(dashboard.getInt("teamId"))
        val dashboardDto = DashboardDto(
            dashboard.getInt("dashboardId"),
            dashboard.getString("dashboardUrl"),
            dashboard.getString("name"),
            dashboard.getInt("refreshRate"),
            team,
            dashboard.getBoolean("hasAccess")
        )
        return dashboardDto
    }
}