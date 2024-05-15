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
            databaseConnection.prepareStatement("INSERT INTO DASHBOARD (TEAMID,NAME,DASHBOARDURL,IMAGEURL) VALUES(?,?,?,?)")
        statement.setInt(1, dashboardDto.getTeam().getId())
        statement.setString(2, dashboardDto.getDashboardName())
        statement.setString(3, dashboardDto.getDashboardUrl())
        statement.setString(4, dashboardDto.getImageUrl())
        statement.executeUpdate()
        statement.close()
    }

    @Throws(ServerErrorException::class)
    override fun getAllDashboards(userId: Int): DashboardCollectionDto {
        val newDashboardCollectionDto: DashboardCollectionDto
        val statement = databaseConnection.prepareStatement("SELECT d.*, t.TEAMNAME, (exists(select * from userinteam where userid = ? and teamid = t.TEAMID)) as hasAccess FROM DASHBOARD d left join team t on d.TEAMID = t.TEAMID ")
        statement.setInt(1, userId)
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAllDashboardsMapper(resultSet)
        statement.close()
        return newDashboardCollectionDto
    }
    @Throws(ServerErrorException::class)
    override fun getDashboardForUser(dashboardId: Int, userId: Int): DashboardDto? {
        val connection = databaseConnection.getConnection()
        val statement = databaseConnection.prepareStatement("SELECT d.*, t.TEAMNAME, (exists(select * from userinteam where userid = ? and teamid = t.TEAMID)) as hasAccess FROM DASHBOARD d left join team t on d.TEAMID = t.TEAMID WHERE DASHBOARDID = ?")
        statement.setInt(1, userId)
        statement.setInt(2, dashboardId)
        val result = statement.executeQuery()
        val dashboard = dashboardsMapper.getDashboardMapper(result)
        statement.close()
        connection.close()
        return dashboard
    }

    @Throws(ServerErrorException::class)
    override fun editDashboard(dashboardDto: DashboardDto) {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("UPDATE DASHBOARD SET TEAMID = ?, NAME = ?, DASHBOARDURL = ?, IMAGEURL = ? WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardDto.getTeam().getId())
        statement.setString(2, dashboardDto.getDashboardName())
        statement.setString(3, dashboardDto.getDashboardUrl())
        statement.setString(4, dashboardDto.getImageUrl())
        statement.setInt(5, dashboardDto.getId())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }
    @Throws(ServerErrorException::class)
    override fun getDashboardUrl(dashboardId: Int): String {
        var url = ""
        val connection = databaseConnection.getConnection()
        val statement = databaseConnection.prepareStatement("SELECT DASHBOARDURL FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardId)
        val result = statement.executeQuery()
        while (result.next()){
            url = result.getString("DASHBOARDURL")
        }
        statement.close()
        connection.close()
        return url
    }
    @Throws(ServerErrorException::class)
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

}