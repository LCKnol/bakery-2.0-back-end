package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/login")
class AuthenticationController @Autowired constructor(
        private var authenticationService: AuthenticationService
){



    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces =[MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val loginResponse = authenticationService.authenticate(loginRequest)

        return ResponseEntity(loginResponse, HttpStatus.OK)
    }
/*
    @Autowired
    fun setAuthenticationService(authenticationService: AuthenticationService){
        this.authenticationService = authenticationService
    }
    */

}

