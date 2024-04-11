package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping

@RestController
@RequestMapping("/login")
class AuthenticationController {

    private val authenticationService = AuthenticationServiceImp()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun login(loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        if (loginRequest.email != "user" || loginRequest.password != "password") {
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }

        val loginResponse = LoginResponseDto("abcdef")
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }



}