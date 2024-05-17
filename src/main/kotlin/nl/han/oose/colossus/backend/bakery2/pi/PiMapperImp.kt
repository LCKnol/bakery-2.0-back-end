package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Primary
@Component
class PiMapperImp : PiMapper {
    override fun mapPis(resultSet: ResultSet): PiCollectionDto {

        val piCollection = PiCollectionDto()
        val pis = arrayListOf<PiDto>()
        while (resultSet.next()) {
            val id = resultSet.getInt("piid")
            val name = resultSet.getString("name")
            val dashboard = resultSet.getString("dashboardname")
            val dashboardId = resultSet.getInt("dashboardid")
            val status = resultSet.getString("status")
            val macAddress = resultSet.getString("macAddress")
            val ipAddress = resultSet.getString("ipaddress")
            val roomNo = resultSet.getString("roomNo")
            val piDTO = PiDto()
            piDTO.setId(id)
            piDTO.setDashboardId(dashboardId)
            piDTO.setName(name)
            piDTO.setDashboardName(dashboard ?: "-")
            piDTO.setMacAddress(macAddress)
            piDTO.setIpAddress(ipAddress)
            piDTO.setStatus(status ?: "-")
            piDTO.setRoomNo(roomNo ?: "-")
            pis.add(piDTO)
        }
        piCollection.setPis(pis)
        return piCollection
    }

    override fun mapPiRequests(resultSet: ResultSet): PiRequestsCollectionDto {
        val piRequestsCollection = PiRequestsCollectionDto()
        val piRequests = arrayListOf<PiRequestDto>()
        while (resultSet.next()) {
            val id = resultSet.getInt("requestid")
            val requestedOn = resultSet.getString("requestedon")
            val macAddress = resultSet.getString("macaddress")
            val ipAddress = resultSet.getString("ipaddress")
            val piRequestDto = PiRequestDto()
            piRequestDto.setId(id)
            piRequestDto.setRequestedOn(requestedOn)
            piRequestDto.setMacAddress(macAddress)
            piRequestDto.setIpAddress(ipAddress)
            piRequests.add(piRequestDto)
        }
        piRequestsCollection.setPiRequests(piRequests)
        return piRequestsCollection
    }

    override fun getPiMapper(resultSet: ResultSet): PiDto {
        val pi = PiDto()
        while (resultSet.next()) {
            val id = resultSet.getInt("piid")
            val name = resultSet.getString("name")
            val dashboardName = resultSet.getString("dashboardname")
            val status = resultSet.getString("status")
            val macAddress = resultSet.getString("macAddress")
            val roomNo = resultSet.getString("roomNo")
            val dashboardId = resultSet.getInt("dashboardId")
            pi.setId(id)
            pi.setName(name)
            pi.setDashboardId(dashboardId)
            pi.setDashboardName(dashboardName ?: "-")
            pi.setMacAddress(macAddress)
            pi.setStatus(status ?: "-")
            pi.setRoomNo(roomNo ?: "-")
        }
        return pi
    }
}