package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.*
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Component
@Primary
class TeamMapperImp: TeamMapper {

    override fun mapUserTeams(resultSet: ResultSet): TeamCollectionDto {
        val teams = ArrayList<TeamDto>()
        while (resultSet.next()) {
            teams.add(this.mapSingleTeam(resultSet))
        }
        val teamCollection = TeamCollectionDto()
        teamCollection.setTeamCollection(teams)
        return teamCollection
    }

    private fun mapSingleTeam(team: ResultSet): TeamDto {
        val teamDto = TeamDto()
        teamDto.setName(team.getString("teamname"))
        teamDto.setId(team.getInt("teamid"))
        return teamDto
    }

    override fun mapTeam(resultSet: ResultSet): TeamDto {
        resultSet.next()
        return mapSingleTeam(resultSet)
    }

    override fun mapTeamInfoCollection(resultSet: ResultSet): TeamInfoCollectionDto {
        val teamInfoCollection = TeamInfoCollectionDto()
        val teamInfoArray: ArrayList<TeamInfoDto> = ArrayList()
        while (resultSet.next()) {
            val currentTeamId = resultSet.getInt("teamid")
            if (!teamInfoArray.any{teamInfo -> teamInfo.getId() == currentTeamId }) {
                val teamInfoDto = TeamInfoDto()
                teamInfoDto.setId(currentTeamId)
                teamInfoDto.setName(resultSet.getString("teamname"))
                teamInfoArray.add(teamInfoDto)
            }
            val currentTeam = teamInfoArray.filter { teamInfo -> teamInfo.getId() == currentTeamId }[0]
            if (!currentTeam.getRooms().any {room -> room.getRoomNo() == resultSet.getString("roomNo")}) {
                val room = RoomDto()
                room.setRoomNo(resultSet.getString("roomNo"))
                currentTeam.getRooms().add(room)
            }

            if (!currentTeam.getMembers().any {member -> member.getId() == resultSet.getInt("userId")}) {
                val user = UserDto(resultSet.getInt("userId"), resultSet.getString("firstname"), resultSet.getString("lastname"), "", "", ArrayList(), false)
                currentTeam.getMembers().add(user)
            }
        }
        teamInfoCollection.setTeamInfoDto(teamInfoArray)
        return teamInfoCollection
    }
}