package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import org.springframework.stereotype.Service


interface AuthenticationService {
    fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto

    fun setAuthenticationDao(authenticationDao: AuthenticationDao)
}