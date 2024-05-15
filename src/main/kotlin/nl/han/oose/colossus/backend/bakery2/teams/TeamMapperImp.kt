package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
@Primary
class TeamMapperImp: TeamMapper {

    override fun mapUserTeams(resultSet: ResultSet): TeamCollectionDto {
        val teams = ArrayList<TeamDto>()
        while (resultSet.next()) {
            teams.add(this.mapTeam(resultSet))
        }
        val teamCollection = TeamCollectionDto()
        teamCollection.setTeamCollection(teams)
        return teamCollection
    }

    private fun mapTeam(team: ResultSet): TeamDto {
        val teamDto = TeamDto()
        teamDto.setName(team.getString("teamname"))
        teamDto.setId(team.getInt("teamid"))
        return teamDto
    }
}