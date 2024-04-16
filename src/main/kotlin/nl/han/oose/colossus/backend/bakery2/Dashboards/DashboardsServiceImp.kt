package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto

class DashboardsServiceImp {

    private var dashboardDao : DashboardsDaoImp = DashboardsDaoImp()
    fun getAllDashboards(): DashboardsDto {
        return dashboardDao.getAllDahsboards()
    }
}