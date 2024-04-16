package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import java.sql.ResultSet

interface PiMapper {
    fun mapPis(resultSet : ResultSet) : PiCollectionDTO

}