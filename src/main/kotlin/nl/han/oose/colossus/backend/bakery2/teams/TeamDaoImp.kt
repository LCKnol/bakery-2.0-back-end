package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerErrorException

@Component
@Primary
class TeamDaoImp: TeamDao {
    @Autowired
    private lateinit var teamMapper: TeamMapper

    @Autowired
    private lateinit var dbConnection: DatabaseConnection

    override fun setDatabaseConnection(connection: DatabaseConnection) {
        dbConnection = connection
    }

    override fun setTeamMapper(mapper: TeamMapper) {
        teamMapper = mapper
    }

    @Throws(ServerErrorException::class)
    override fun getTeamsFromUser(userId: Int): TeamCollectionDto {
        val query = "SELECT * FROM TEAM WHERE TEAMID IN (SELECT TEAMID FROM USERINTEAM WHERE USERID = ?)"
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, userId)
        val resultSet = preparedStatement.executeQuery()
        val teams = teamMapper.mapUserTeams(resultSet)
        preparedStatement.close()
        connection.close()
        return teams
    }

    @Throws(ServerErrorException::class)
    override fun getTeams(userId: Int): TeamCollectionDto {
        val query = "SELECT * FROM TEAM WHERE TEAMID IN (SELECT TEAMID FROM USERINTEAM WHERE USERID = ?)"
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, userId)
        val resultSet = preparedStatement.executeQuery()
        val teams = teamMapper.mapUserTeams(resultSet)
        preparedStatement.close()
        connection.close()
        return teams
    }
    @Throws
    override fun getTeamsNotInRoom(roomNo: String): TeamCollectionDto {
        val query = "SELECT * FROM TEAM WHERE TEAMID NOT IN (SELECT TEAMID FROM TEAMINROOM WHERE ROOMNO = ?)"
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, roomNo)
        val resultSet = preparedStatement.executeQuery()
        val teams = teamMapper.mapUserTeams(resultSet)
        preparedStatement.close()
        connection.close()
        return teams
    }


    @Throws(ServerErrorException::class)
    override fun assignUserToTeam(userId: Int, teamId: Int) {
        val query = "INSERT INTO USERINTEAM (USERID,TEAMID) VALUES(?,?)"
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, userId)
        preparedStatement.setInt(2, teamId)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }

    @Throws(ServerErrorException::class)
    override fun removeUserFromTeam(userId: Int, teamId: Int) {
        val query = "DELETE FROM USERINTEAM WHERE USERID = ? AND TEAMID = ?"
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setInt(1, userId)
        preparedStatement.setInt(2, teamId)
        preparedStatement.executeUpdate()
        preparedStatement.close()
        connection.close()
    }

    override fun getAllTeams(): TeamCollectionDto {
        val query = "SELECT * FROM TEAM "
        val connection = dbConnection.getConnection()
        val preparedStatement = connection.prepareStatement(query)
        val resultSet = preparedStatement.executeQuery()
        val teams = teamMapper.mapUserTeams(resultSet)
        preparedStatement.close()
        connection.close()
        return teams
    }

    override fun addTeam(teamInfoDto: TeamInfoDto) {
        val connection = dbConnection.getConnection()

        val preparedStatement1 = connection.prepareStatement("INSERT INTO TEAM (TEAMNAME) VALUES (?)")
        preparedStatement1.setString(1, teamInfoDto.getName())
        preparedStatement1.executeUpdate()

        preparedStatement1.close()
        connection.close()
    }

    override fun getTeam(teamName: String): TeamDto {
        val connection = dbConnection.getConnection()

        val preparedStatement = connection.prepareStatement("SELECT * FROM TEAM WHERE TEAMNAME = ?")
        preparedStatement.setString(1, teamName)
        val resultSet = preparedStatement.executeQuery()
        val team = teamMapper.mapTeam(resultSet)
        preparedStatement.close()
        connection.close()
        return team
    }
}