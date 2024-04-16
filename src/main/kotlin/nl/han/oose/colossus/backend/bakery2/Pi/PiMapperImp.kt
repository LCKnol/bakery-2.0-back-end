package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import nl.han.oose.colossus.backend.bakery2.dto.PiDTO
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import java.sql.ResultSet

class PiMapperImp : PiMapper {
    override fun mapPis(resultSet: ResultSet): PiCollectionDTO {

        var piCollection = PiCollectionDTO()
        var pis = arrayListOf<PiDTO>()
        while (resultSet.next()) {
            val id = resultSet.getInt("piid")
            val name = resultSet.getString("name")
            val dashboard = resultSet.getString("dashboardname")
            val piDTO = PiDTO()
            piDTO.setId(id)
            piDTO.setName(name)
            piDTO.setDisplay(dashboard)
            pis.add(piDTO)
        }
        piCollection.setPis(pis)
        return piCollection
    }
}