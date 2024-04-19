package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class DashboardsServiceImp :DashboardsService {

    @Autowired
    private lateinit var dashboardDao : DashboardsDao


    override fun setDashboardDao(dao: DashboardsDao) {
        dashboardDao = dao
    }

    override fun addDashboard(dashboardDto: DashboardDto) {
        dashboardDao.addDashboard(dashboardDto)
    }

    override fun getAllDashboards(): DashboardCollectionDto {
        return dashboardDao.getAllDashboards()
    }

    override fun editDashboard(dashboardDto: DashboardDto) {
        dashboardDao.editDashboard(dashboardDto)
    }
}