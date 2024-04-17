package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import java.sql.SQLException

@Component
@Primary
class DashboardsDaoImp:DashboardsDao {


    private lateinit var databaseConnection: DatabaseConnection

    private lateinit var dashboardsMapper: DashboardsMapper


    @Autowired
    fun setDashboardsMapper(dashboardsMapper: DashboardsMapper) {
        this.dashboardsMapper = dashboardsMapper
    }

    @Autowired
    fun setDatabaseConnection (databaseConnection: DatabaseConnection ) {
        this.databaseConnection = databaseConnection
    }

    @Throws(ServerErrorException::class)
    override fun getAllDashboards() :DashboardCollectionDto {

        var newDashboardCollectionDto: DashboardCollectionDto
        val statement = databaseConnection.prepareStatement("SELECT * FROM dashboard ")
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAlldashboardsMapper(resultSet)
        statement.close()

        return newDashboardCollectionDto
    }

}