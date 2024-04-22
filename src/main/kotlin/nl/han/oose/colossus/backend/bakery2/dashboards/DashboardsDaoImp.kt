package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import java.sql.SQLException

@Component
@Primary
class DashboardsDaoImp : DashboardsDao {

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
        val statement =
            databaseConnection.prepareStatement("INSERT INTO DASHBOARD (USERID,NAME,DASHBOARDURL,IMAGEURL) VALUES(?,?,?,?)")
        statement.setInt(1, dashboardDto.getUserId())
        statement.setString(2, dashboardDto.getName())
        statement.setString(3, dashboardDto.getDashboardUrl())
        statement.setString(4, dashboardDto.getImageURL())
        statement.executeUpdate()
        statement.close()
    }

    @Throws(ServerErrorException::class)
    override fun getAllDashboards(): DashboardCollectionDto {
        val newDashboardCollectionDto: DashboardCollectionDto
        val statement = databaseConnection.prepareStatement("SELECT * FROM DASHBOARD ")
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAlldashboardsMapper(resultSet)
        statement.close()
        return newDashboardCollectionDto
    }

    override fun getDashboard(dashboardId: Int): DashboardDto? {
        val connection = databaseConnection.getConnection()
        val statement = databaseConnection.prepareStatement("SELECT * FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardId)
        val result = statement.executeQuery()
        val dashboard = dashboardsMapper.getDashboardMapper(result)
        statement.close()
        connection.close()
        return dashboard
    }

    @Throws(ServerErrorException::class)
    override fun editDashboard(dashboardDto: DashboardDto) {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("UPDATE DASHBOARD SET USERID = ?, NAME = ?, DASHBOARDURL = ?, IMAGEURL = ? WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardDto.getUserId())
        statement.setString(2, dashboardDto.getName())
        statement.setString(3,dashboardDto.getDashboardUrl())
        statement.setString(4,dashboardDto.getImageURL())
        statement.setInt(5, dashboardDto.getId())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    override fun deleteDashboard(dashboardId: Int) {
        val query = "DELETE FROM DASHBOARD WHERE DASHBOARDID = ?"
        try {
            val statement = databaseConnection.prepareStatement(query)
            statement.setInt(1, dashboardId)
            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    override fun getUserIdFromDashboard(dashboardId: Int): Int? {
        val query = "select USERID from dashboard where DASHBOARDID = ?"
        try {
            val statement = databaseConnection.prepareStatement(query)
            statement.setInt(1, dashboardId)
            val resultSet = statement.executeQuery()
            val result =  dashboardsMapper.getUserIdMapper(resultSet)
            statement.close()
            return result
        } catch (e: SQLException) {
            println(e.message)
            return null
        }
    }

}