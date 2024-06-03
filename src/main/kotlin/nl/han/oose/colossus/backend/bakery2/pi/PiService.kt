package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.springframework.messaging.simp.SimpMessagingTemplate

interface PiService {
    fun getPisFromUser(user: Int): PiCollectionDto
    fun setPiDao(dao: PiDao)
    fun getAllPis(): PiCollectionDto
    fun getAllPiRequests(): PiRequestsCollectionDto
    fun editPi(piDto: PiDto, userId: Int)
    fun updatePiIp(piSignUpRequestDto: PiSignUpRequestDto)
    fun getPi(piId: Int?, macAddress: String?): PiDto
    fun addPi(macAddress: String, ipAddress: String, name: String, roomno: String)
    fun declinePiRequest(macAddress: String)
    fun handlePiRequest(macAddress: String, isAccepted: Boolean)
    fun assignDashboardToPi(request: PiDto)
    fun setDashboardDao(dao: DashboardsDao)
    fun rebootPi(piId : Int)
    fun setMessagingTemplate(messagingTemplate: SimpMessagingTemplate)
    fun pingPi(piId: Int)
    fun setPiStatus(piStatus: PiStatus, piId: Int)
    fun setTvPower(piId: Int, option: Boolean)
    fun updateAllPis()
    fun pingAllPis()
    fun rebootAllPis()
}