package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.users.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component
import java.util.*


@Primary
@Component
class AuthenticationServiceImp : AuthenticationService {
    @Autowired
    private lateinit var authenticationDao: AuthenticationDao

    @Autowired
    private lateinit var userDao: UserDao


    override fun setAuthenticationDao(authenticationDao: AuthenticationDao) {
        this.authenticationDao = authenticationDao
    }

    override fun setUserDao(userDao: UserDao) {
        this.userDao = userDao
    }

    override fun isAdmin(token: String): Boolean {
        val user = userDao.getUser(token)
        return user?.getIsAdmin() ?: false
    }

    override fun authenticate(email: String, password: String): LoginResponseDto {

        val passwordHash = authenticationDao.findPassword(email)

        try {
            if (!BCrypt.checkpw(password, passwordHash)) {
                throw HttpUnauthorizedException("Invalid login credentials")
            }
        } catch (e: IllegalArgumentException) {
            println("BCrypt encountered an invalid salt: " + e.message)
            throw HttpUnauthorizedException("Invalid login credentials")
        }

        val token = this.generateToken()
        authenticationDao.insertToken(email, token)
        return LoginResponseDto(token, userDao.getUser(token)!!.getIsAdmin())
    }

    override fun handleGoogleSignIn(email: String, userExists: Boolean) : LoginResponseDto {
        if (!userExists) {
            return LoginResponseDto("", false)
        }

        val token = this.generateToken()
        authenticationDao.insertToken(email, token)
        return LoginResponseDto(token, userDao.getUser(token)!!.getIsAdmin())
    }


    override fun destroySession(token: String) {
        authenticationDao.deleteSession(token)
    }

    override fun validateToken(token: String) {
        if (!authenticationDao.tokenExists(token)) {
            throw HttpUnauthorizedException("Invalid token")
        }
    }

    private fun generateToken(): String {
        var token: String
        do {
            token = UUID.randomUUID().toString()
        } while (authenticationDao.tokenExists(token))
        return token
    }
}