package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
@Primary
class DashboardMapperImp:DashboardsMapper {

    override fun getAlldashboardsMapper(resultSet: ResultSet):DashboardCollectionDto {
        var newDashboardCollectionDto = DashboardCollectionDto()

        if (!resultSet.isBeforeFirst) {
        }
        while (resultSet.next()) {
            var newDashboardDto =
                DashboardDto(resultSet.getInt("DASHBOARDID"), resultSet.getString("DASHBOARDURL"), resultSet.getString("NAME"),resultSet.getString("IMAGEURL"))
            newDashboardCollectionDto.getDashboards().add(newDashboardDto)
        }
        return newDashboardCollectionDto
    }
}