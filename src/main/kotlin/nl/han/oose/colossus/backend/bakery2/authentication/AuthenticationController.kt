package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/authenticate")
class AuthenticationController {

    @Autowired
    private lateinit var authenticationService: AuthenticationService

    @Autowired
    private lateinit var headerService: HeaderService

    fun setAuthenticationService(service: AuthenticationService) {
        this.authenticationService = service
    }

    fun setTokenService(headerService: HeaderService) {
        this.headerService = headerService
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<LoginResponseDto> {

        val loginResponse = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword())
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @DeleteMapping
    @Authenticate
    fun logout(): ResponseEntity<HttpStatus> {

        authenticationService.destroySession(headerService.getToken())
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}