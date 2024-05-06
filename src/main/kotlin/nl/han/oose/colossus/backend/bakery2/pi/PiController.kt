package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pis")
class PiController {
    @Autowired
    private lateinit var piService: PiService

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

    fun setPiService(service: PiService) {
        piService = service
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getPis(): ResponseEntity<PiCollectionDto> {
        val token = headerService.getToken()
        val user = userService.getUserId(token)
        val pisResponse = piService.getPis(user)
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }


    @GetMapping(path = ["all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getAllPis(): ResponseEntity<PiCollectionDto> {
        val token = headerService.getToken()
        val pisResponse = piService.getAllPis()
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }

    @GetMapping(path = ["requests"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getAllPiRequests(): ResponseEntity<PiRequestsCollectionDto> {
        val token = headerService.getToken()
        val pisResponse = piService.getAllPiRequests()
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }
    @GetMapping(path = ["/{piId}"], produces = ["application/json"])
    fun getPi(@PathVariable("piId") piId: Int): ResponseEntity<PiDto> {
        val result = this.piService.getPi(piId)
        return ResponseEntity(result, HttpStatus.OK)
    }
    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun editPi(@RequestBody piDto: PiDto): ResponseEntity<HttpStatus> {
        val token = this.headerService.getToken()
        val userId = this.userService.getUserId(token)
        this.piService.editPi(piDto,userId)
        return ResponseEntity(HttpStatus.OK)
    }



}
