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
            val macAdress = resultSet.getString("macadress")
            val piDTO = PiDto()
            piDTO.setId(id)
            piDTO.setName(name)
            piDTO.setMacAdress(macAdress)
            piDTO.setDisplay(dashboard ?: "-")
            piDTO.setStatus(status ?: "-")
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
            val macAdress = resultSet.getString("macadress")
            val piRequestDto = PiRequestDto()
            piRequestDto.setId(id)
            piRequestDto.setRequestedOn(requestedOn)
            piRequestDto.setMacAdress(macAdress)
            piRequests.add(piRequestDto)
        }
        piRequestsCollection.setPiRequests(piRequests)
        return piRequestsCollection

    }
}
