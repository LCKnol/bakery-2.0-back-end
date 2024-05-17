package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.pi.PiService
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

    @Autowired
    private lateinit var piService: PiService

    fun setPiSignUpService(piSignUpService: PiSignUpService) {
        this.piSignUpService = piSignUpService
    }

    fun setPiService(piService: PiService) {
        this.piService = piService
    }

    @MessageMapping("/sign-up-pi")
    fun signUpPi(request: PiSignUpRequestDto) {
        if (piSignUpService.checkPiExists(request.getMacAddress())) {
            piService.handlePiRequest(request.getMacAddress(), true)
            val pi = piService.getPi(null, request.getMacAddress())
            piService.assignDashboardToPi(pi)
        }
        // check if pi already signed up
        else if (!piSignUpService.checkPiSignUpExists(request.getMacAddress())){
            piSignUpService.createSignUpRequest(request.getMacAddress())
        }
    }
}