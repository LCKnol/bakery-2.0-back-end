package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
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

    override fun getDashboard(dashboardId: Int, userId: Int): DashboardDto {
        val dashboard = dashboardDao.getDashboardForUser(dashboardId, userId)
            ?: throw HttpNotFoundException("Dashboard does not exist")
        return dashboard
    }

    override fun addDashboard(dashboardDto: DashboardDto) {
        dashboardDao.addDashboard(dashboardDto)
    }

    override fun deleteDashboard(dashboardId: Int) {
        piDao.removeDashboardFromPis(dashboardId)
        dashboardDao.deleteDashboard(dashboardId)
    }

    override fun getAllDashboards(userId: Int): DashboardCollectionDto {
        return dashboardDao.getAllDashboards(userId)
    }

    override fun editDashboard(dashboardDto: DashboardDto) {
        dashboardDao.editDashboard(dashboardDto)
    }


}