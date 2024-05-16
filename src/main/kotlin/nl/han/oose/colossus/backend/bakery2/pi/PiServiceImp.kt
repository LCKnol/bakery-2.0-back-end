package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiAcceptDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSetDashboardDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.SocketResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.messaging.simp.SimpMessagingTemplate

@Primary
@Component
class PiServiceImp : PiService {
    @Autowired
    private lateinit var piDao: PiDao

    @Autowired
    private lateinit var dashboardDao: DashboardsDao

    @Autowired
    private lateinit var messagingTemplate: SimpMessagingTemplate



    override fun setPiDao(dao: PiDao) {
        piDao = dao
    }

    override fun setDashboardDao(dao: DashboardsDao) {
        dashboardDao = dao
    }

    override fun setMessagingTemplate(messagingTemplate: SimpMessagingTemplate) {
        this.messagingTemplate = messagingTemplate
    }


    override fun getPis(user: Int): PiCollectionDto {
        val pis = piDao.getPis(user)
        return pis
    }

    override fun getAllPis(): PiCollectionDto {
        return piDao.getAllPis()
    }

    override fun getAllPiRequests(): PiRequestsCollectionDto {
        return piDao.getAllPiRequests()
    }

    override fun addPi(macAddress: String, name: String, roomno: String) {
        piDao.insertPi(macAddress, name, roomno)
        deletePiRequest(macAddress)

    }

    override fun declinePiRequest(macAddress: String) {
        deletePiRequest(macAddress)
    }

    private fun deletePiRequest(macAddress: String) {
        piDao.deletePiRequest(macAddress)
    }

    override fun handlePiRequest(macAddress: String, isAccepted: Boolean) {
        val piAcceptDto = PiAcceptDto()
        piAcceptDto.setIsAccepted(isAccepted)
        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setBody(piAcceptDto)
        socketResponseDto.setInstruction("init-pi")
        messagingTemplate.convertAndSend("/topic/init-pi/$macAddress", socketResponseDto)
    }


    override fun editPi(piDto: PiDto, userId: Int) {
        piDao.editPi(piDto)
    }

    override fun getPi(piId: Int): PiDto {
        val pi = piDao.getPi(piId) ?: throw HttpNotFoundException("pi does not exist")
        return pi
    }

    override fun assignDashboardToPi(request: PiDto) {
        piDao.assignDashboard(request)
        val assignedDashboard = PiSetDashboardDto()
        val dashboardUrl = dashboardDao.getDashboardUrl(request.getDashboardId())
        assignedDashboard.setUrl(dashboardUrl)

        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setBody(assignedDashboard)
        socketResponseDto.setInstruction("set-dashboard")
        messagingTemplate.convertAndSend("/topic/pi-listener/${request.getMacAddress()}", socketResponseDto)

    }
}