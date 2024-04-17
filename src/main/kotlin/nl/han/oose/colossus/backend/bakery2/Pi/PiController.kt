package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TokenDto
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
class PiController{
    @Autowired
        private lateinit var piService : PiService
    @Autowired
        private lateinit var userService : UserService

    @Autowired
    private lateinit var tokenService: TokenService


    @GetMapping(produces =[MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getPis(): ResponseEntity<PiCollectionDTO> {
        val token = tokenService.getToken()
        val user = userService.getUser(token)
        val pisResponse = piService.getPis(user)
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }

}