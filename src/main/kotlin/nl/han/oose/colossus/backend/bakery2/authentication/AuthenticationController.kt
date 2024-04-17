package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*


@RestController

class AuthenticationController {

@Autowired
private lateinit var authenticationService: AuthenticationService


    @RequestMapping("/login")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val loginResponse = authenticationService.authenticate(loginRequest)

        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @RequestMapping("/register")
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatus> {
        //TODO: Only allow with admin privileges
        authenticationService.registerUser(userDto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}

