package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.users.UserDao


interface AuthenticationService {
    fun authenticate(email: String, password: String): LoginResponseDto

    fun destroySession(token: String)

    fun validateToken(token: String)

    fun setAuthenticationDao(authenticationDao: AuthenticationDao)

    fun setUserDao(userDao: UserDao)

    fun isAdmin(token: String): Boolean
}