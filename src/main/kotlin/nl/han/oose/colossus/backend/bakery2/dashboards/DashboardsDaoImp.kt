package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
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

    override fun setDashboardsMapper(mapper: DashboardsMapper) {
        dashboardsMapper = mapper
    }


    @Throws(ServerErrorException::class)
    override fun addDashboard(dashboardDto: DashboardDto) {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("INSERT INTO DASHBOARD (USERID,NAME,DASHBOARDURL,IMAGEURL) VALUES(?,?,?,?) ")
        statement.setInt(1,dashboardDto.getUserId())
        statement.setString(2,dashboardDto.getName())
        statement.setString(3,dashboardDto.getURl())
        statement.setString(4,dashboardDto.getImageURL())
        println(statement)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }


    @Throws(ServerErrorException::class)
    override fun getAllDashboards() :DashboardCollectionDto {
        val connection = databaseConnection.getConnection()
        val newDashboardCollectionDto: DashboardCollectionDto
        val statement = connection .prepareStatement("SELECT * FROM DASHBOARD ")
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAlldashboardsMapper(resultSet)
        statement.close()
        connection.close()

        return newDashboardCollectionDto
    }

}