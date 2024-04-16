package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto


interface AuthenticationService {
    fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto

    fun validateToken(token: String)

    fun registerUser(userDto: UserDto)
}