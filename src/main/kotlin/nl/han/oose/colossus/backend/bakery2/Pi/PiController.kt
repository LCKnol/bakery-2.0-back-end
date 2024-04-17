package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.Users.UserServiceImp
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import nl.han.oose.colossus.backend.bakery2.dto.TokenDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun getPis(@RequestBody token: TokenDto): ResponseEntity<PiCollectionDTO> {
        //authenticator.authenticate
        val user = userService.getUser(token.token)
        val pisResponse = piService.getPis(user)
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }
}