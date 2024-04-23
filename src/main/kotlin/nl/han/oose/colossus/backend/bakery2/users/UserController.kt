package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import nl.han.oose.colossus.backend.bakery2.token.Authenticate
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var tokenService: TokenService

    @GetMapping(produces =[MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getUserInfo(): ResponseEntity<UserInfoDto> {
        val token = tokenService.getToken()
        val user = userService.getUserInfo(token)
        return ResponseEntity(user, HttpStatus.OK)
    }

}