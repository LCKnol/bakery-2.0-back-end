package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dto.GoogleTokenDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.LoginResponseDto
import nl.han.oose.colossus.backend.bakery2.dto.NewGoogleUserDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.h2.engine.User
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

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var googleAuthService: GoogleAuthService

    fun setAuthenticationService(service: AuthenticationService) {
        this.authenticationService = service
    }

    fun setTokenService(headerService: HeaderService) {
        this.headerService = headerService
    }

    fun setUserService(userService: UserService) {
        this.userService = userService
    }

    fun setGoogleAuthService(googleAuthService: GoogleAuthService) {
        this.googleAuthService = googleAuthService
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


    @PostMapping(path = ["/google"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signInWithGoogle(@RequestBody googleTokenDto: GoogleTokenDto): ResponseEntity<LoginResponseDto> {

        val googleUserData = googleAuthService.verifyToken(googleTokenDto.getJwtToken())
        val loginResponse = authenticationService.handleGoogleSignIn(googleUserData.email, userService.emailExists(googleUserData.email))
        return ResponseEntity(loginResponse, HttpStatus.OK)
    }

    @PostMapping(path= ["/google/register"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun registerGoogleUser(@RequestBody newGoogleUserDto: NewGoogleUserDto): ResponseEntity<HttpStatus> {
        googleAuthService.verifyToken(newGoogleUserDto.getJwtToken())
        userService.registerUser(newGoogleUserDto.getUserDto())
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping(path = ["/google/verify-token"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun verifyJwtToken(@RequestBody googleTokenDto: GoogleTokenDto): ResponseEntity<HttpStatus> {

        googleAuthService.verifyToken(googleTokenDto.getJwtToken())
        return ResponseEntity(HttpStatus.OK)
    }


}