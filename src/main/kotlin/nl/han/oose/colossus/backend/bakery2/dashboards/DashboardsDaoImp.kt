package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException

@Component
@Primary
class DashboardsDaoImp:DashboardsDao {

    @Autowired
    private lateinit var databaseConnection: DatabaseConnection

    @Autowired
    private lateinit var dashboardsMapper: DashboardsMapper


    override fun setDatabaseConnection(connection: DatabaseConnection) {
        databaseConnection = connection
    }

    // Setter function for dashboardsMapper
    override fun setDashboardsMapper(mapper: DashboardsMapper) {
        dashboardsMapper = mapper
    }

    @Throws(ServerErrorException::class)
    override fun getAllDashboards() :DashboardCollectionDto {

        val newDashboardCollectionDto: DashboardCollectionDto
        val statement = databaseConnection.prepareStatement("SELECT * FROM DASHBOARD ")
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAlldashboardsMapper(resultSet)
        statement.close()

        return newDashboardCollectionDto
    }

}