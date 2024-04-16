package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.inject.Inject

@Component
class DashboardsDaoImp:DashboardsDao {



    override fun getAllDahsboards():DashboardsDto {

        var newDashboardsDto  = DashboardsDto()
        var newDashboardDto = DashboardDto(1,"test","NewDashboard")
        var newDashboardDto2 = DashboardDto(12,"test2","NewDashboard2")
        newDashboardsDto.getDashboards().add(newDashboardDto2)
        newDashboardsDto.getDashboards().add(newDashboardDto)

        return newDashboardsDto
    }


}