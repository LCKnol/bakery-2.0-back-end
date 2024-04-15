package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDaoImp
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Service
class AuthenticationServiceImp : AuthenticationService {

    private var authenticationDao: AuthenticationDao? = null

    override fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto {
        if (!authenticationDao?.isValidUser(loginRequest.getEmail(), loginRequest.getPassword())!!) {
            throw HttpUnauthorizedException("Invalid login credentials")
        }
        val token = generateToken()
        authenticationDao?.insertToken(token)
        return LoginResponseDto(generateToken())

    }

    override fun setAuthenticationDao(authenticationDao: AuthenticationDao) {
        this.authenticationDao = authenticationDao
    }

    private fun generateToken(): String {
        var token = ""
        do {
            token = UUID.randomUUID().toString()
        } while(authenticationDao?.tokenExists(token) == true)
        return token
    }


}