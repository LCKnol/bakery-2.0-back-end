package nl.han.oose.colossus.backend.bakery2.dto

class NewGoogleUserDto {

    private var jwtToken: String = ""
    private lateinit var userDto : UserDto

    fun getJwtToken(): String {
        return this.jwtToken
    }

    fun setJwtToken(jwtToken: String) {
        this.jwtToken = jwtToken
    }

    fun getUserDto(): UserDto {
        return this.userDto
    }

    fun setUserDto(userDto: UserDto) {
        this.userDto = userDto
    }
}