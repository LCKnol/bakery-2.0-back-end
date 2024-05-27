package nl.han.oose.colossus.backend.bakery2.dto

class TeamInfoCollectionDto {
    private var teamInfoDto: ArrayList<TeamInfoDto> = ArrayList()

    fun getTeamInfoDto(): ArrayList<TeamInfoDto> {
        return teamInfoDto
    }

    fun setTeamInfoDto(teamInfoDto: ArrayList<TeamInfoDto>) {
        this.teamInfoDto = teamInfoDto
    }
}