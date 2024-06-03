package nl.han.oose.colossus.backend.bakery2.dto

class GoogleTokenDto {

    private var jwtToken: String = ""

    fun getJwtToken(): String {
        return this.jwtToken
    }

    fun setJwtToken(jwtToken: String) {
        this.jwtToken = jwtToken
    }
}