package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto

interface TeamDao {
    fun setDatabaseConnection(connection: DatabaseConnection)
    fun setTeamMapper(mapper: TeamMapper)
    fun getTeams(userId: Int): TeamCollectionDto
    fun getTeamsNotInRoom(roomNo: String): TeamCollectionDto
    fun getTeamsFromUser(userId: Int): TeamCollectionDto
    fun assignUserToTeam(userId: Int, teamId: Int)
    fun removeUserFromTeam(userId: Int, teamId: Int)
    fun getAllTeams(): TeamCollectionDto
}