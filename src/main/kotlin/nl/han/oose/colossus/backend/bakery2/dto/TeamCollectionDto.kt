package nl.han.oose.colossus.backend.bakery2.dto

class TeamCollectionDto {
    private var teamCollection: ArrayList<TeamDto> = ArrayList()
    fun setTeamCollection(roomCollection: ArrayList<TeamDto>) {
        this.teamCollection = roomCollection
    }

    fun getTeamCollection(): ArrayList<TeamDto> {
        return teamCollection
    }
}