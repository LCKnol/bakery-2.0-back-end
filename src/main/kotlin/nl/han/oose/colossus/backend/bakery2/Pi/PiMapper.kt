package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import java.sql.ResultSet

interface PiMapper {
    fun mapPis(resultSet : ResultSet) : PiCollectionDto

}