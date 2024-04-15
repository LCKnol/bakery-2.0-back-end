package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
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
@RequestMapping("/user")
class UserController @Autowired constructor(
        private var userService: UserService
){
    //private val authenticator = AuthenticatorImp()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun getUserInfo(@RequestBody token: String): ResponseEntity<UserInfoDTO> {
        //authenticator.authenticate
        println(token)
        val user = userService.getUserInfo(token)
        return ResponseEntity(user, HttpStatus.OK)
    }

}