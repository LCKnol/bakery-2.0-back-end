package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DashboardsServiceImp :DashboardsService {

    private lateinit var dashboardDao : DashboardsDao

    @Autowired
    fun setAuthenticationService(dashboardDao : DashboardsDao) {
        this.dashboardDao = dashboardDao
    }
    override fun getAllDashboards(): DashboardsDto {
        return dashboardDao.getAllDahsboards()
    }
}