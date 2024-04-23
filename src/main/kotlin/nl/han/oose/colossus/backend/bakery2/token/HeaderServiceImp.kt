package nl.han.oose.colossus.backend.bakery2.token

import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
@Primary
class HeaderServiceImp: HeaderService {
    override fun getToken(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw HttpUnauthorizedException("No authorization token provided.")
        return token
    }
}