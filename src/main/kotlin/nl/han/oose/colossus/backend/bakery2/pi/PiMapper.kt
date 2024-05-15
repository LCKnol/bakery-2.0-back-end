package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import java.sql.ResultSet

interface PiMapper {
    fun mapPis(resultSet : ResultSet) : PiCollectionDto
    fun mapPiRequests(resultSet: ResultSet): PiRequestsCollectionDto
    fun getPiMapper(resultSet: ResultSet): PiDto?
}