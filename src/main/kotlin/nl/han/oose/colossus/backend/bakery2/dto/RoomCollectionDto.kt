package nl.han.oose.colossus.backend.bakery2.dto

class RoomCollectionDto {

    private var roomCollection: ArrayList<RoomDto> = ArrayList()

    fun getRooms(): ArrayList<RoomDto> {
        return roomCollection
    }

    fun setRooms(newRooms: List<RoomDto>) {
        roomCollection.clear()
        roomCollection.addAll(newRooms)
    }
}