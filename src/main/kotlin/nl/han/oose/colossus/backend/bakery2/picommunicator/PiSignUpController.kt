package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiAcceptDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.SocketResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class PiSignUpController {

    @Autowired
    private lateinit var piSignUpService: PiSignUpService

    fun setPiSignUpService(piSignUpService: PiSignUpService) {
        this.piSignUpService = piSignUpService
    }

    @MessageMapping("/sign-up-pi")
    fun signUpPi(request: PiSignUpRequestDto) {
        piSignUpService.createSignUpRequest(request.getMacAddress())
    }
}