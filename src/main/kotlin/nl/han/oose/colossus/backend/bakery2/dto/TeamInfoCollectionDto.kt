package nl.han.oose.colossus.backend.bakery2.dto

class TeamInfoCollectionDto {
    private var teamInfoCollection: ArrayList<TeamInfoDto> = ArrayList()

    fun getTeamInfoCollection(): ArrayList<TeamInfoDto> {
        return teamInfoCollection
    }

    fun setTeamInfoCollection(teamInfoCollection: ArrayList<TeamInfoDto>) {
        this.teamInfoCollection = teamInfoCollection
    }
}