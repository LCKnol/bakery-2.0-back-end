package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import nl.han.oose.colossus.backend.bakery2.header.Admin
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.beans.factory.annotation.Autowired
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


    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatus> {
        userService.registerUser(userDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @Authenticate
    @Admin
    @GetMapping(path = ["/all"],produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers(): ResponseEntity<UserCollectionDto> {
        var users = userService.getAllUsers()
        return ResponseEntity(users, HttpStatus.OK)
    }

    @Authenticate
    @Admin
    @DeleteMapping(path = ["/{userId}"])
    fun deleteUser(@PathVariable userId: Int): ResponseEntity<UserCollectionDto> {
        userService.deleteUser(userId)
        return ResponseEntity(HttpStatus.OK)
    }

    @Authenticate
    @Admin
    @PostMapping(path = ["/assignAdminRights"])
    fun assignAdminRightsToUser(@RequestBody userDto: UserDto): ResponseEntity<UserCollectionDto> {
        userService.assignAdminRightsToUser(userDto)
        return ResponseEntity(HttpStatus.OK)
    }
}