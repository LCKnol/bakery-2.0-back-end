package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.token.Authenticate
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/authenticate")
class AuthenticationController {

@Autowired
private lateinit var authenticationService: AuthenticationService

@Autowired
private lateinit var tokenService: TokenService


    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val loginResponse = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @DeleteMapping
    @Authenticate
    fun logout(): ResponseEntity<HttpStatus> {

        authenticationService.destroySession(tokenService.getToken())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PostMapping(path = ["/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerUser(@RequestBody userDto: UserDto): ResponseEntity<HttpStatus> {
        //TODO: Only allow with admin privileges
        authenticationService.registerUser(userDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

}

