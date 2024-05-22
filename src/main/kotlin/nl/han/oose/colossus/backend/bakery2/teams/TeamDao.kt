package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto

interface TeamDao {
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setTeamMapper(mapper: TeamMapper)
    fun getTeams(userId: Int): TeamCollectionDto
    fun assignUserToTeam(userId: Int, teamId: Int)
}