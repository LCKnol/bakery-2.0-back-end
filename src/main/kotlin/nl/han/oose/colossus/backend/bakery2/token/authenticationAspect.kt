package nl.han.oose.colossus.backend.bakery2.token

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class AuthenticationAspect(val authenticationService: AuthenticationService) {

    @Before("@annotation(Authenticate)")
    fun authenticateAccess() {
        val request = (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes).request
        val token = request.getHeader("Authorization") ?: throw SecurityException("No authorization token provided.")

        if (!authenticationService.checkToken(token)) {
            throw HttpUnauthorizedException("Invalid token.")
        }
    }
}