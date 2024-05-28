package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiStatus
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiPingDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
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
            piService.updatePiIp(request)

            // Sleep 1 second so pi has time to assign onto the topic/pi-listener
            Thread.sleep(1000)
            val pi = piService.getPi(null, request.getMacAddress())

            piService.setPiStatus(PiStatus.ONLINE, pi.getId())
            if (pi.getDashboardId() != 0) {
                piService.assignDashboardToPi(pi)
            }
        }
        // check if pi already signed up
        else {
            piSignUpService.createSignUpRequest(request.getMacAddress(), request.getIpAddress())
        }
    }

    @MessageMapping("/ping")
    fun pingPi(ping: PiPingDto) {
        val pi = piService.getPi(null, ping.getMacAddress())
        piService.setPiStatus(PiStatus.ONLINE, pi.getId())
    }
}