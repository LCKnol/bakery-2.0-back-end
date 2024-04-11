package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@RestController
@RequestMapping("/login")
class AuthenticationController {

    private val authenticationService = AuthenticationServiceImp()

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        if (loginRequest.getEmail() != "Avisi@outlook.com" || loginRequest.getPassword() != "AvisiPassword") {
            println(loginRequest.getEmail() + loginRequest.getEmail())
            return ResponseEntity(HttpStatus.UNAUTHORIZED)
        }
        println("works")
        val loginResponse = LoginResponseDto("1234-1234-1234")
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }



}