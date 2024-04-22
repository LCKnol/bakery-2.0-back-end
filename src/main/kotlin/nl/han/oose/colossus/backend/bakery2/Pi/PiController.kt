package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TokenDto
import nl.han.oose.colossus.backend.bakery2.token.Authenticate
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.Authenticator

@RestController
@RequestMapping("/pis")
class PiController {
    @Autowired
    private lateinit var piService: PiService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var tokenService: TokenService

    fun setUserService(service: UserService) {
        userService = service
    }
    fun setTokenService(service: TokenService) {
        tokenService = service
    }
    fun setPiService(service: PiService) {
        piService = service
    }
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getPis(): ResponseEntity<PiCollectionDto> {
        val token = tokenService.getToken()
        val user = userService.getUserId(token)
        val pisResponse = piService.getPis(user)
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }

}