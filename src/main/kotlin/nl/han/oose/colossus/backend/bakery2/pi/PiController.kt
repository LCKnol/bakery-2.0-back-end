package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Admin
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiAcceptDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.SocketResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/pis")
class PiController {

    @Autowired
    private lateinit var messagingTemplate: SimpMessagingTemplate

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
        val roomno = piDto.getRoomno()
        piService.addPi(macAddress, name, roomno)
        initPi(macAddress,true)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @DeleteMapping(path=["init/{macAddress}"])
    @Admin
    @Authenticate
    fun declinePiRequest(@PathVariable macAddress: String): ResponseEntity<HttpStatus> {
        piService.declinePiRequest(macAddress)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }


    private fun initPi(macAddress: String, isAccepted: Boolean) {
        val piAcceptDto = PiAcceptDto()
        piAcceptDto.setIsAccepted(isAccepted)
        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setBody(piAcceptDto)
        socketResponseDto.setInstruction("init-pi")
        messagingTemplate.convertAndSend("/topic/init-pi/$macAddress", socketResponseDto)
    }

}
