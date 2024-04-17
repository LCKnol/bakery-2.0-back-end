package nl.han.oose.colossus.backend.bakery2.dto

class RoomDto {
    private var roomNo: String = null.toString()

    fun getRoomNo(): String {return this.roomNo}
    fun setRoomNo(roomNo: String) {this.roomNo = roomNo}
}