package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.context.annotation.Primary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Primary
@Component
class AuthenticationServiceImp :AuthenticationService {
    @Autowired
    private lateinit var  authenticationDao: AuthenticationDao
    private lateinit var  tokenService: TokenService



    override fun authenticate(email: String, password: String): LoginResponseDto {

        val passwordHash = authenticationDao.findPassword(email)

        if (!BCrypt.checkpw(password, passwordHash)) {
            throw HttpUnauthorizedException("Invalid login credentials")
        }

        val token = tokenService.generateToken()
        authenticationDao.insertToken(email, token)
        return LoginResponseDto(token)
    }

    override fun destroySession(token: String) {
        authenticationDao.deleteSession(token)
    }

    override fun validateToken(token: String) {
        if (!authenticationDao.tokenExists(token)) {
            throw HttpUnauthorizedException("Invalid token")
        }
    }

    override fun registerUser(userDto: UserDto) {
        userDto.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()))
        authenticationDao.insertUser(userDto)
    }

    override fun setAuthenticationDao(authenticationDao: AuthenticationDao) {
        this.authenticationDao = authenticationDao
    }

    fun setTokenService(tokenService: TokenService) {
        this.tokenService = tokenService
    }

}