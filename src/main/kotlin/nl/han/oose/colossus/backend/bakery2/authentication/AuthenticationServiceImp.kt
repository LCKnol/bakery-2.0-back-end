package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDaoImp
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException

class AuthenticationServiceImp : AuthenticationService {

    private val authenticationDao = AuthenticationDaoImp()

    override fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto {
        if (!authenticationDao.isValidUser(loginRequest.getEmail(), loginRequest.getPassword())) {
            throw HttpUnauthorizedException("Invalid login credentials")
        }
        val token = generateToken()
        authenticationDao.insertToken(token)
        return LoginResponseDto(generateToken())

    }

    private fun generateToken(): String {
        //TODO: Token generation
        var token = ""
        do {
            token = "1234-1234-1234"
        } while(authenticationDao.tokenExists(token))
        return token
    }


}