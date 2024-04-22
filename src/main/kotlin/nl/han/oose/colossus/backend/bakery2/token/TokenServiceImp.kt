package nl.han.oose.colossus.backend.bakery2.token

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.util.*

@Component
@Primary
class TokenServiceImp: TokenService {
    private lateinit var  authenticationDao: AuthenticationDao
    override fun getToken(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw HttpUnauthorizedException("No authorization token provided.")
        return token
    }

    override fun generateToken(): String {
        var token: String
        do {
            token = UUID.randomUUID().toString()
        } while(authenticationDao.tokenExists(token))
        return token
    }
}