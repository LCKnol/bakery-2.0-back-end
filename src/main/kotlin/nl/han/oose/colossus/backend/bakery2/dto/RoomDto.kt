package nl.han.oose.colossus.backend.bakery2.dto

class RoomDto {
    private var roomNo: String = null.toString()

    private var teamCollection: TeamCollectionDto = TeamCollectionDto()

    fun getRoomNo(): String {return this.roomNo}
    fun setRoomNo(roomNo: String) {this.roomNo = roomNo}

    fun getTeamCollection(): TeamCollectionDto {
        return this.teamCollection
    }

    fun setTeamCollection(teamCollection: TeamCollectionDto) {
        this.teamCollection = teamCollection
    }
}