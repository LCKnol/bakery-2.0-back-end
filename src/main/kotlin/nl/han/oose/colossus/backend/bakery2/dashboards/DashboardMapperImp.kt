package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
@Primary
class DashboardMapperImp : DashboardsMapper {

    override fun getAlldashboardsMapper(resultSet: ResultSet): DashboardCollectionDto {
        val newDashboardCollectionDto = DashboardCollectionDto()

        while (resultSet.next()) {
            val newDashboardDto =
                DashboardDto(
                    resultSet.getInt("DASHBOARDID"),
                    resultSet.getString("DASHBOARDURL"),
                    resultSet.getString("NAME"),
                    resultSet.getString("IMAGEURL"),
                    resultSet.getInt("USERID")
                )
            newDashboardCollectionDto.getDashboards().add(newDashboardDto)
        }
        return newDashboardCollectionDto
    }

    override fun getDashboardMapper(resultSet: ResultSet): DashboardDto? {

        var dashboard: DashboardDto? = null

        while (resultSet.next()) {
            dashboard = DashboardDto(
                resultSet.getInt("dashboardId"),
                resultSet.getString("dashboardUrl"),
                resultSet.getString("name"),
                resultSet.getString("imageUrl"),
                resultSet.getInt("userId")
            )
        }
        return dashboard
    }

    override fun getUserIdMapper(resultSet: ResultSet): Int? {
        var userId: Int? = null
        while (resultSet.next()) {
            userId = resultSet.getInt("USERID")
        }
        return userId
    }
}