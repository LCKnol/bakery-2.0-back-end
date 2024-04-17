package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
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



    override fun authenticate(loginRequest: LoginRequestDto): LoginResponseDto {

        val passwordHash = authenticationDao.findPassword(loginRequest.getEmail())

        if (!BCrypt.checkpw(loginRequest.getPassword(), passwordHash)) {
            throw HttpUnauthorizedException("Invalid login credentials")
        }

        val token = generateToken()
        authenticationDao.insertToken(loginRequest.getEmail(), token)
        return LoginResponseDto(generateToken())
    }

    override fun validateToken(token: String) {
        if (!authenticationDao.tokenExists(token)) {
            throw HttpUnauthorizedException("Invalid token")
        }
    }

    override fun registerUser(userDto: UserDto) {
        userDto.password = BCrypt.hashpw(userDto.password, BCrypt.gensalt())
        authenticationDao.insertUser(userDto)
    }

    private fun generateToken(): String {
        var token: String
        do {
            token = UUID.randomUUID().toString()
        } while(authenticationDao.tokenExists(token))
        return token
    }


}