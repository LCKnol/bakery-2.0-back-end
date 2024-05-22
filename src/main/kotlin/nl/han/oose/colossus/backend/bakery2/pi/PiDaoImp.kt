package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
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
    private lateinit var databaseConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        databaseConnection = connection
    }

    override fun setPiMapper(mapper: PiMapper) {
        piMapper = mapper
    }

    @Throws(ServerErrorException::class)
    override fun getPis(user: Int): PiCollectionDto {
        val connection = databaseConnection.getConnection()
        val preparedStatement =
            connection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE p.roomno IN (SELECT roomno FROM TEAMINROOM WHERE teamid IN (SELECT teamid FROM USERINTEAM WHERE userid = ?))")
        preparedStatement.setInt(1, user)
        val resultSet = preparedStatement.executeQuery()
        val pis = piMapper.mapPis(resultSet)
        resultSet.close()
        preparedStatement.close()
        connection.close()
        return pis
    }

    @Throws(ServerErrorException::class)
    override fun removeDashboardFromPis(dashboardId: Int) {
        val connection = databaseConnection.getConnection()
        val query = "UPDATE PI SET DASHBOARDID = NULL WHERE DASHBOARDID = ?"
        val statement = connection.prepareStatement(query)
        statement.setInt(1, dashboardId)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun getAllPis(): PiCollectionDto {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID")
        val result = statement.executeQuery()
        val pis = piMapper.mapPis(result)
        statement.close()
        connection.close()
        return pis
    }

    @Throws(ServerErrorException::class)
    override fun getAllPiRequests(): PiRequestsCollectionDto {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT * FROM PIREQUEST")
        val result = statement.executeQuery()
        val piRequests = piMapper.mapPiRequests(result)
        statement.close()
        connection.close()
        return piRequests
    }

    @Throws(ServerErrorException::class)
    override fun insertPi(macAddress: String, ipAddress: String, name: String, roomno: String) {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("INSERT INTO PI (macAddress, ipAddress, name, roomno) VALUES(?, ?, ?, ?)")
        statement.setString(1, macAddress)
        statement.setString(2, ipAddress)
        statement.setString(3, name)
        statement.setString(4, roomno)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun deletePiRequest(macAddress: String) {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("DELETE FROM PIREQUEST WHERE macAddress = ?")
        statement.setString(1, macAddress)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun editPi(piDto: PiDto) {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("UPDATE PI SET NAME = ?, ROOMNO = ? WHERE PIID = ?")
        statement.setString(1, piDto.getName())
        statement.setString(2, piDto.getRoomNo())
        statement.setInt(3, piDto.getId())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun updatePiIp(piSignUpRequestDto: PiSignUpRequestDto) {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("UPDATE PI SET IPADDRESS = ? WHERE MACADDRESS = ?")
        statement.setString(1, piSignUpRequestDto.getIpAddress())
        statement.setString(2, piSignUpRequestDto.getMacAddress())
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun getPi(piId: Int?, macAddress: String?): PiDto? {
        val connection = databaseConnection.getConnection()
        val query = if (piId != null) {
            "SELECT p.*, d.NAME AS dashboardname, d.dashboardId FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE PIID = ?"
        } else if (macAddress != null) {
            "SELECT p.*, d.NAME AS dashboardname, d.dashboardId FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE MACADDRESS = ?"
        } else {
            return null
        }
        val statement =
            connection.prepareStatement(query)
        if (piId != null) statement.setInt(1, piId) else statement.setString(1, macAddress)
        val result = statement.executeQuery()
        val pi = piMapper.getPiMapper(result)
        statement.close()
        connection.close()
        return pi
    }

    override fun getPi(piId: Int): PiDto? {
        val connection = databaseConnection.getConnection()
        val statement =
            connection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM PI p LEFT JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE PIID =?")
        statement.setInt(1, piId)
        val result = statement.executeQuery()
        val pi = piMapper.getPiMapper(result)
        statement.close()
        connection.close()
        return pi
    }

    @Throws(ServerErrorException::class)
    override fun assignDashboard(piId: Int, dashboardId: Int) {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("UPDATE PI SET DASHBOARDID = ? WHERE PIID = ?");
        statement.setInt(1, dashboardId)
        statement.setInt(2, piId)
        statement.executeUpdate()
        statement.close()
        connection.close()
    }

    override fun getMacAddress(piId: Int):String {
        val connection = databaseConnection.getConnection()
        val statement = connection.prepareStatement("SELECT macAddress FROM PI WHERE PIID = ?");
        statement.setInt(1, piId)
        val resultSet = statement.executeQuery()
        val result = piMapper.mapMacAddress(resultSet)
        statement.close()
        connection.close()
        return result

    }
}