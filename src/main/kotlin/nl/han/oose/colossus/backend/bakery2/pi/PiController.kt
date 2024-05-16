package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Admin
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
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
        val userId = headerService.getUserId()
        val pisResponse = piService.getPis(userId)
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }


    @GetMapping(path = ["all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Admin
    @Authenticate
    fun getAllPis(): ResponseEntity<PiCollectionDto> {
        val pisResponse = piService.getAllPis()
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }

    @GetMapping(path = ["requests"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Admin
    @Authenticate
    fun getAllPiRequests(): ResponseEntity<PiRequestsCollectionDto> {
        val pisResponse = piService.getAllPiRequests()
        return ResponseEntity(pisResponse, HttpStatus.OK)
    }

    @PostMapping(path = ["init"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Admin
    @Authenticate
    fun initPi(@RequestBody piDto: PiDto): ResponseEntity<HttpStatus> {
        val macAddress = piDto.getMacAddress()
        val name = piDto.getName()
        val roomNo = piDto.getRoomNo()
        piService.addPi(macAddress, name, roomNo)
        piService.handlePiRequest(macAddress,true)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping(path=["init/{macAddress}"])
    @Admin
    @Authenticate
    fun declinePiRequest(@PathVariable macAddress: String): ResponseEntity<HttpStatus> {
        piService.declinePiRequest(macAddress)
        piService.handlePiRequest(macAddress, false)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    @GetMapping(path = ["/{piId}"], produces = ["application/json"])
    fun getPi(@PathVariable("piId") piId: Int): ResponseEntity<PiDto> {
        val result = this.piService.getPi(piId, null)
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

    @PostMapping("setdashboard",consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun assignDashboardToPi(@RequestBody request: PiDto): ResponseEntity<HttpStatus> {
        piService.assignDashboardToPi(request)
        return ResponseEntity(HttpStatus.OK)
    }

}
