package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.users.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

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

    override fun getTeams(userId: Int): TeamCollectionDto {
        val query = "SELECT * FROM TEAM WHERE TEAMID IN (SELECT TEAMID FROM USERINTEAM WHERE USERID = ?)"
        val conn = dbConnection.getConnection()
        val preparedStatement = conn.prepareStatement(query)
        preparedStatement.setInt(1, userId)
        val resultSet = preparedStatement.executeQuery()
        val teams = teamMapper.mapUserTeams(resultSet)
        preparedStatement.close()
        conn.close()
        return teams
    }
}