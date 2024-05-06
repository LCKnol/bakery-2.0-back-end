package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
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

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setPiMapper(mapper: PiMapper) {
        piMapper = mapper
    }

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

    override fun removeDashboardFromPis(dashboardId: Int) {
        val query = "UPDATE PI SET DASHBOARDID = NULL WHERE DASHBOARDID = ?"
        try {
            val statement = dbConnection.prepareStatement(query)
            statement.setInt(1, dashboardId)
            statement.executeUpdate()
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    @Throws(ServerErrorException::class)
    override fun getAllPis(): PiCollectionDto {
        val connection = dbConnection.getConnection()
        val statement = dbConnection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID")
        val result = statement.executeQuery()
        val pis = piMapper.mapPis(result)
        statement.close()
        connection.close()
        return pis
    }

    @Throws(ServerErrorException::class)
    override fun getAllPiRequests(): PiRequestsCollectionDto {
        val connection = dbConnection.getConnection()
        val statement = dbConnection.prepareStatement("SELECT * FROM PIREQUEST")
        val result = statement.executeQuery()
        val piRequests = piMapper.mapPiRequests(result)
        statement.close()
        connection.close()
        return piRequests
    }


    @Throws(ServerErrorException::class)
    override fun editPi(piDto: PiDto)  {
        val connection = dbConnection.getConnection()
        val statement =
            connection.prepareStatement("UPDATE PI SET NAME = ?, ROOMNO = ?, WHERE PIID = ?")
        statement.setInt(1, piDto.getId())
        statement.setString(2, piDto.getName())
        statement.setString(3, piDto.getRoomNo())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }


    override fun getPi(piId: Int): PiDto? {
        val connection = dbConnection.getConnection()
        val statement = dbConnection.prepareStatement("SELECT * FROM PI WHERE PIID = ?")
        statement.setInt(1, piId)
        val result = statement.executeQuery()
        val pi= piMapper.getPiMapper(result)
        statement.close()
        connection.close()
        return pi

    }
}