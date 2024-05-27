package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Admin
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserService
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
        val ipAddress = piDto.getIpAddress()
        val name = piDto.getName()
        val roomNo = piDto.getRoomNo()
        piService.addPi(macAddress, ipAddress, name, roomNo)
        piService.handlePiRequest(macAddress, true)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping(path = ["init/{macAddress}"])
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
        this.piService.editPi(piDto, userId)
        return ResponseEntity(HttpStatus.OK)
    }

    @PostMapping("setdashboard", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun assignDashboardToPi(@RequestBody request: PiDto): ResponseEntity<HttpStatus> {
        piService.assignDashboardToPi(request)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/reboot/{piId}")
    @Authenticate
    fun rebootPi(@PathVariable piId:Int): ResponseEntity<HttpStatus> {
        piService.rebootPi(piId)
        return ResponseEntity(HttpStatus.OK)
    }


    @GetMapping("ping/{piId}")
    @Authenticate
    fun pingPi(@PathVariable("piId") piId: Int): ResponseEntity<HttpStatus> {
        this.piService.setPiStatus(PiStatus.OFFLINE, piId)
        this.piService.pingPi(piId)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("tv/{piId}/{option}")
    @Authenticate
    fun setTvPower(@PathVariable piId: Int, @PathVariable option: Boolean): ResponseEntity<HttpStatus> {
        this.piService.setTvPower(piId,option)
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/update")
    @Authenticate
    fun updateAllPis(): ResponseEntity<HttpStatus>{
        this.piService.updateAllPis()
        return ResponseEntity(HttpStatus.OK)
    }
    @GetMapping("/pingAll")
    @Authenticate
    fun pingAllPis(): ResponseEntity<HttpStatus>{
        this.piService.pingAllPis()
        return ResponseEntity(HttpStatus.OK)
    }
    @GetMapping("/rebootAll")
    @Authenticate
    fun reebootAllPis(): ResponseEntity<HttpStatus>{
        this.piService.rebootAllPis()
        return ResponseEntity(HttpStatus.OK)
    }
}