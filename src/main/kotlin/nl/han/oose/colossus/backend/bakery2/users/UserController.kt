package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var headerService: HeaderService

    fun setUserService(service: UserService) {
        userService = service
    }

    fun setTokenService(service: HeaderService) {
        headerService = service
    }
    @GetMapping(produces =[MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getUserInfo(): ResponseEntity<UserInfoDto> {
        val token = headerService.getToken()
        val user = userService.getUserInfo(token)
        return ResponseEntity(user, HttpStatus.OK)
    }

    @PostMapping(path = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatus> {
        //TODO: Only allow with admin privileges
        userService.registerUser(userDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

}