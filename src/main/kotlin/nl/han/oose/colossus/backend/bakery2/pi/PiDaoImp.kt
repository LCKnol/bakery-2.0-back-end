package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException
import java.sql.SQLException

@Primary
@Component
class PiDaoImp : PiDao {
    @Autowired
    private lateinit var piMapper: PiMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection

    @Throws(ServerErrorException::class)
    override fun getPis(user: Int): PiCollectionDto {
        val preparedStatement =
            dbConnection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE p.roomno IN (SELECT roomno FROM TEAMINROOM WHERE teamid IN (SELECT teamid FROM USERINTEAM WHERE userid = ?))")
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