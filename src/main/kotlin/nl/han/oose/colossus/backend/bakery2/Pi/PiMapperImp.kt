package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import java.sql.ResultSet

@Primary
@Component
class PiMapperImp : PiMapper {
    override fun mapPis(resultSet: ResultSet): PiCollectionDto {

        var piCollection = PiCollectionDto()
        var pis = arrayListOf<PiDto>()
        while (resultSet.next()) {
            val id = resultSet.getInt("piid")
            val name = resultSet.getString("name")
            val dashboard = resultSet.getString("dashboardname")
            val status = resultSet.getString("status")
            val piDTO = PiDto()
            piDTO.setId(id)
            piDTO.setName(name)
            piDTO.setDisplay(dashboard)
            piDTO.setStatus(status)
            pis.add(piDTO)
        }
        piCollection.setPis(pis)
        return piCollection
    }
}