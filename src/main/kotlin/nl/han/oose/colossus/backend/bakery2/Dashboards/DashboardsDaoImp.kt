package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto

class DashboardsDaoImp {

    private var dashboardMapper:DashboardMapperImp = DashboardMapperImp()


    fun getAllDahsboards():DashboardsDto {

        var newDashboardsDto  = DashboardsDto()
        var newDashboardDto = DashboardDto(1,"test","NewDashboard")
        newDashboardsDto.getDashboards().add(newDashboardDto)

        return newDashboardsDto
    }


}