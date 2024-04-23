package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto


interface AuthenticationService {
    fun authenticate(email: String, password: String): LoginResponseDto

    fun destroySession(token: String)

    fun validateToken(token: String)

    fun setAuthenticationDao(authenticationDao: AuthenticationDao)
}