package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class PingPiService {

    @Autowired
    private lateinit var piService: PiService

    fun setPiService(piService: PiService) {
        this.piService = piService
    }

    fun getPiService(): PiService {
        return this.piService
    }

    @Scheduled(fixedRate = 300000)
    fun pingPis() {
        val allPis = piService.getAllPis()
        allPis.getPis().forEach { pi: PiDto ->
            piService.setPiStatus(PiStatus.OFFLINE, pi.getId())
            piService.pingPi(pi.getId())
        }
    }
}