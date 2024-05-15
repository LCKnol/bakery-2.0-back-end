package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import java.sql.ResultSet

interface TeamMapper {
    fun mapUserTeams(resultSet: ResultSet): TeamCollectionDto
}