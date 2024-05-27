package nl.han.oose.colossus.backend.bakery2.dto

class TeamInfoDto {
    private var id: Int = 0
    private var name: String = ""
    private var members: ArrayList<UserInfoDto> = ArrayList()
    private var rooms: ArrayList<RoomDto> = ArrayList()

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getMembers(): ArrayList<UserInfoDto> {
        return members
    }

    fun setMembers(members: List<UserInfoDto>) {
        this.members = ArrayList(members) // Properly assign the list to members
    }

    fun getRooms(): ArrayList<RoomDto> {
        return rooms
    }

    fun setRooms(rooms: List<RoomDto>) {
        this.rooms = ArrayList(rooms) // Properly assign the list to rooms
    }
}
