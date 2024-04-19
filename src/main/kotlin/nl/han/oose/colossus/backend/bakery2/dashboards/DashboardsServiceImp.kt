package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.Pi.PiDao
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class DashboardsServiceImp : DashboardsService {

    @Autowired
    private lateinit var dashboardDao: DashboardsDao

    @Autowired
    private lateinit var piDao: PiDao

    override fun setDashboardDao(dao: DashboardsDao) {
        dashboardDao = dao
    }

    override fun setPiDao(dao: PiDao) {
        piDao = dao
    }

    override fun addDashboard(dashboardDto: DashboardDto) {
        dashboardDao.addDashboard(dashboardDto)
    }

    override fun deleteDashboard(dashboardId: Int, userId: Int) {
        if (dashboardDao.userOwnsDashboard(dashboardId, userId)) {
            piDao.setDashboardsNull(dashboardId)
            dashboardDao.deleteDashboard(dashboardId)
        } else {
            throw HttpUnauthorizedException("Dashboard belongs to different user")
        }
    }

    override fun getAllDashboards(): DashboardCollectionDto {
        return dashboardDao.getAllDashboards()
    }
}