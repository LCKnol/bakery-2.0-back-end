package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto

class AuthenticationServiceImp : AuthenticationService {

    override fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto {
        return LoginResponseDto("sdasd")
    }
}