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
            val status = resultSet.getString("status")
            val macAddress = resultSet.getString("macaddress")
            val roomNo = resultSet.getString("roomno")
            val piDTO = PiDto()
            piDTO.setId(id)
            piDTO.setName(name)
            piDTO.setMacAddress(macAddress)
            piDTO.setDisplay(dashboard ?: "-")
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
            val requestedOn= resultSet.getString("requestedon")
            val macAddress = resultSet.getString("macaddress")
            val piRequestDto = PiRequestDto()
            piRequestDto.setId(id)
            piRequestDto.setRequestedOn(requestedOn)
            piRequestDto.setMacAddress(macAddress)
            piRequests.add(piRequestDto)
        }
        piRequestsCollection.setPiRequests(piRequests)
        return piRequestsCollection

    }
}
