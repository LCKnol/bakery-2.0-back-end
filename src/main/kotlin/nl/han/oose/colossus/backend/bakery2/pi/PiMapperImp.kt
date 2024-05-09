package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.*
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
            val roomNo = resultSet.getString("roomNo")

            val piDTO = PiDto(id, name,  status,dashboard, roomNo)
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
            val requestTime = resultSet.getString("requestedon")
            val macAdress = resultSet.getString("macadress")
            val piRequestDto = PiRequestDto()
            piRequestDto.setId(id)
            piRequestDto.setRequestTime(requestTime)
            piRequestDto.setMacAdress(macAdress)
            piRequests.add(piRequestDto)
        }
        piRequestsCollection.setPiRequests(piRequests)
        return piRequestsCollection

    }

    override fun getPiMapper(resultSet: ResultSet): PiDto? {
        if (resultSet.next()) {
            return PiDto(
                resultSet.getInt("piId"),
                resultSet.getString("name"),
                resultSet.getString("status"),
                resultSet.getString("dashboardname"),
                resultSet.getString("roomNo")
            )
        }
        return null
    }

}
