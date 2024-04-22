package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.SQLException

@Primary
@Component
class PiDaoImp : PiDao {
    @Autowired
    private lateinit var piMapper: PiMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection


    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setPiMapper(mapper: PiMapper) {
        piMapper = mapper
    }

    override fun getPis(user: Int): PiCollectionDto {
        val preparedStatement =
            dbConnection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM Pi p INNER JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE p.roomno IN (SELECT roomno FROM teaminroom WHERE teamid IN (SELECT teamid FROM userinteam WHERE userid = ?))")
        preparedStatement.setInt(1, user)
        val resultSet = preparedStatement.executeQuery()
        val pis = piMapper.mapPis(resultSet)
        resultSet.close()
        preparedStatement.close()
        return pis
    }

    override fun setDashboardsNull(dashboardId: Int) {
        val query = "UPDATE PI SET DASHBOARDID = NULL WHERE DASHBOARDID = ?"
        try {
            val statement = dbConnection.prepareStatement(query)
            statement.setInt(1, dashboardId)
            statement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}