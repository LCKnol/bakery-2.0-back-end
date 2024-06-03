package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.*
import nl.han.oose.colossus.backend.bakery2.users.UserDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Primary
@Component
class PiServiceImp : PiService {

    @Autowired
    private lateinit var piDao: PiDao

    @Autowired
    private lateinit var dashboardDao: DashboardsDao

    @Autowired
    private lateinit var messagingTemplate: SimpMessagingTemplate

    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var headerService: HeaderService


    override fun setPiDao(dao: PiDao) {
        piDao = dao
    }

    override fun setDashboardDao(dao: DashboardsDao) {
        dashboardDao = dao
    }

    override fun setMessagingTemplate(messagingTemplate: SimpMessagingTemplate) {
        this.messagingTemplate = messagingTemplate
    }

    override fun rebootPi(piId: Int) {
        reboot(piId)
    }

    override fun pingPi(piId: Int) {
        ping(piId)
    }

    private fun ping(piId: Int) {
        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setInstruction("ping")
        val macAddress = this.piDao.getMacAddress(piId)
        messagingTemplate.convertAndSend("/topic/pi-listener/$macAddress", socketResponseDto)
    }

    private fun reboot(piId: Int) {
        val piRebootDto = PiRebootDto()
        piRebootDto.setReboot(true)
        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setInstruction("reboot")
        socketResponseDto.setBody(piRebootDto)
        val macAddress = piDao.getMacAddress(piId)
        messagingTemplate.convertAndSend("/topic/pi-listener/$macAddress", socketResponseDto)
    }

    override fun setPiStatus(piStatus: PiStatus, piId: Int) {
        this.piDao.updateStatus(piStatus.toString(), piId)
    }

    override fun setTvPower(piId: Int, option: Boolean) {
        checkIfUserOwnsPi(piId)
        val socketResponseDto = SocketResponseDto()
        val piSetTvDto = PiSetTvDto()
        piSetTvDto.setOption(option)
        socketResponseDto.setInstruction("set-tv")
        socketResponseDto.setBody(piSetTvDto)
        val macAddress = this.piDao.getMacAddress(piId)
        messagingTemplate.convertAndSend("/topic/pi-listener/$macAddress", socketResponseDto)
    }


    override fun getPisFromUser(user: Int): PiCollectionDto {
        val pis = piDao.getPisFromUser(user)
        return pis
    }

    override fun updateAllPis() {
        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setInstruction("update-pi")
        val pis = piDao.getAllPis()
        for (pi in pis.getPis()) {
            val macAddress = pi.getMacAddress()
            messagingTemplate.convertAndSend("/topic/pi-listener/$macAddress", socketResponseDto)
        }
    }

    override fun pingAllPis() {
        val pis = piDao.getAllPis()
        for (pi in pis.getPis()) {
            ping(pi.getId())
        }
    }

    override fun rebootAllPis() {
        val pis = piDao.getAllPis()
        for (pi in pis.getPis()) {
            reboot(pi.getId())
        }
    }

    fun checkIfUserOwnsPi(piId: Int ) {
        val currentUser = userDao.getUser(headerService.getToken())
        val pis = piDao.getPisFromUser(currentUser!!.getId())
        if (!pis.getPis().any{ piDto -> piDto.getId() == piId} && !currentUser.getIsAdmin() ){
            throw HttpUnauthorizedException("You do not have access this pi")
        }
    }

    override fun getAllPis(): PiCollectionDto {
        return piDao.getAllPis()
    }

    override fun getAllPiRequests(): PiRequestsCollectionDto {
        return piDao.getAllPiRequests()
    }

    override fun addPi(macAddress: String, ipAddress: String, name: String, roomno: String) {
        piDao.insertPi(macAddress, ipAddress, name, roomno)
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

    override fun updatePiIp(piSignUpRequestDto: PiSignUpRequestDto) {
        piDao.updatePiIp(piSignUpRequestDto)
    }

    override fun getPi(piId: Int?, macAddress: String?): PiDto {
        val pi = piDao.getPi(piId, macAddress) ?: throw HttpNotFoundException("pi does not exist")
        return pi
    }

    override fun assignDashboardToPi(request: PiDto) {
        checkIfUserOwnsPi(request.getId())
        piDao.assignDashboard(request.getId(), request.getDashboardId())

        val assignedDashboard = PiSetDashboardDto()
        val dashboardUrl = dashboardDao.getDashboardUrl(request.getDashboardId())
        val dashboardRefresh = dashboardDao.getDashboardRefresh(request.getDashboardId())
        assignedDashboard.setUrl(dashboardUrl)
        assignedDashboard.setRefresh(dashboardRefresh)

        val socketResponseDto = SocketResponseDto()
        socketResponseDto.setBody(assignedDashboard)
        socketResponseDto.setInstruction("set-dashboard")
        val macAddress = request.getMacAddress()
        messagingTemplate.convertAndSend("/topic/pi-listener/$macAddress", socketResponseDto)
    }
}