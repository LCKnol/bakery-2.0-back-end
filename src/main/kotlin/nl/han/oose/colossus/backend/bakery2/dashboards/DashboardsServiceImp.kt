package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.Pi.PiDao
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpForbiddenException
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

    override fun getDashboard(dashboardId: Int): DashboardDto {
        val dashboard = dashboardDao.getDashboard(dashboardId)
        if (dashboard == null) {
            throw HttpNotFoundException("Dashboard does not exist")
        }
        return dashboard
    }

    override fun addDashboard(dashboardDto: DashboardDto) {
        dashboardDao.addDashboard(dashboardDto)
    }

    override fun deleteDashboard(dashboardId: Int, userId: Int) {
        checkUserPerms(dashboardId, userId)
        piDao.setDashboardsNull(dashboardId)
        dashboardDao.deleteDashboard(dashboardId)
    }

    override fun getAllDashboards(): DashboardCollectionDto {
        return dashboardDao.getAllDashboards()
    }

    override fun editDashboard(dashboardDto: DashboardDto, userId: Int) {
        checkUserPerms(dashboardDto.getId(), userId)
        dashboardDto.setUserId(userId)
        dashboardDao.editDashboard(dashboardDto)
    }

    private fun checkUserPerms(dashboardId: Int, userId: Int) {
        if (dashboardDao.getUserIdFromDashboard(dashboardId) != userId) {
            throw HttpForbiddenException("Dashboard belongs to different user")
        }
    }


}