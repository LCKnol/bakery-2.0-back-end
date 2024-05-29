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
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("INSERT INTO DASHBOARD (TEAMID,NAME,DASHBOARDURL,REFRESHRATE) VALUES(?,?,?,?)")
        statement.setInt(1, dashboardDto.getTeam().getId())
        statement.setString(2, dashboardDto.getDashboardName())
        statement.setString(3, dashboardDto.getDashboardUrl())
        statement.setInt(4, dashboardDto.getDashboardRefresh())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun getAllDashboards(userId: Int): DashboardCollectionDto {
        val newDashboardCollectionDto: DashboardCollectionDto
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("SELECT d.*, t.TEAMNAME, (exists(select * from USERINTEAM where userid = ? and teamid = t.TEAMID)) as hasAccess FROM DASHBOARD d left join TEAM t on d.TEAMID = t.TEAMID ")
        statement.setInt(1, userId)
        val resultSet = statement.executeQuery()
        newDashboardCollectionDto = dashboardsMapper.getAllDashboardsMapper(resultSet)
        statement.close()
        connection.close()
        return newDashboardCollectionDto
    }

    @Throws(ServerErrorException::class)
    override fun getDashboardForUser(dashboardId: Int, userId: Int): DashboardDto? {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("SELECT d.*, t.TEAMNAME, (exists(select * from USERINTEAM where userid = ? and teamid = t.TEAMID)) as hasAccess FROM DASHBOARD d left join TEAM t on d.TEAMID = t.TEAMID WHERE DASHBOARDID = ?")
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
            connection.prepareStatement("UPDATE DASHBOARD SET TEAMID = ?, NAME = ?, DASHBOARDURL = ?, REFRESHRATE = ? WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardDto.getTeam().getId())
        statement.setString(2, dashboardDto.getDashboardName())
        statement.setString(3, dashboardDto.getDashboardUrl())
        statement.setInt(4, dashboardDto.getId())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun getDashboardUrl(dashboardId: Int): String {
        var url = ""
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT DASHBOARDURL FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardId)
        val result = statement.executeQuery()
        while (result.next()) {
            url = result.getString("DASHBOARDURL")
        }
        statement.close()
        connection.close()
        return url
    }

    @Throws(ServerErrorException::class)
    override fun getDashboardRefresh(dashboardId: Int): Int {
        var refresh = 0
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT REFRESHRATE FROM DASHBOARD WHERE DASHBOARDID = ?")
        statement.setInt(1, dashboardId)
        val result = statement.executeQuery()
        while (result.next()) {
            refresh = result.getInt("REFRESHRATE")
        }
        statement.close()
        connection.close()
        return refresh
    }

    @Throws(ServerErrorException::class)
    override fun deleteDashboard(dashboardId: Int) {
        val query = "DELETE FROM DASHBOARD WHERE DASHBOARDID = ?"
        try {
            val connection = databaseConnection.getConnection()
            val statement = connection.prepareStatement(query)
            statement.setInt(1, dashboardId)
            statement.executeUpdate()
            statement.close()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}