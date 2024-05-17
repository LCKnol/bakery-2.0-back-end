package nl.han.oose.colossus.backend.bakery2.header

import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Component
@Primary
class HeaderServiceImp: HeaderService {
    @Autowired
    private lateinit var userService: UserService

    override fun getUserId(): Int {
        val token = this.token()
        val userId = userService.getUserId(token)
        return userId
    }

    override fun getToken(): String {
        return this.token()
    }

    private fun token(): String {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw HttpUnauthorizedException("No authorization token provided.")

        return token
    }
}