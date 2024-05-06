package nl.han.oose.colossus.backend.bakery2.header
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
@Primary
class CustomHeaderInterceptor : HandlerInterceptor {
    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @Authenticate
    override fun preHandle(
            request: HttpServletRequest,
            response: HttpServletResponse,
            handler: Any
    ): Boolean {
        response.addHeader("admin", authenticationService.isAdmin(request.getHeader("Authorization")).toString())
        return true // make sure to return true to proceed with the request flow
    }
}
