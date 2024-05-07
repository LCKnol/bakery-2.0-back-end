package nl.han.oose.colossus.backend.bakery2.header

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpForbiddenException
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes


@Aspect
@Component
class IsAdminAspect {

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @Before("@annotation(IsAdmin)")
    fun isAdmin() {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw HttpUnauthorizedException("No authorization token provided.")
        if (!authenticationService.isAdmin(token)) {
            throw HttpForbiddenException("No admin perms on this account.")
        }
    }
}