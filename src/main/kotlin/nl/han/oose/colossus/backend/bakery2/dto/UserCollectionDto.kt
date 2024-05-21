package nl.han.oose.colossus.backend.bakery2.dto

class UserCollectionDto {

    private var userCollection: ArrayList<UserDto> = ArrayList()

    fun setUserCollection(userCollection: ArrayList<UserDto>) {
        this.userCollection = userCollection
    }

    fun getUserCollection(): ArrayList<UserDto> {
        return userCollection
    }



}