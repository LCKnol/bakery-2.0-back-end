package nl.han.oose.colossus.backend.bakery2.token

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
@Primary
class TokenServiceImp: TokenService {
    override fun getToken(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw SecurityException("No authorization token provided.")
        return token
    }
}