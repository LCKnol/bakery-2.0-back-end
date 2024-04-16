package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class DashboardsServiceImp :DashboardsService {

    private lateinit var dashboardDao : DashboardsDao

    @Autowired
    fun setDashboardsDao(dashboardDao : DashboardsDao) {
        this.dashboardDao = dashboardDao
    }
    override fun getAllDashboards(): DashboardCollectionDto {
        return dashboardDao.getAllDashboards()
    }
}