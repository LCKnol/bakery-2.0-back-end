package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto

interface PiService {

    fun getPis(user: Int):PiCollectionDto

    fun setPiDao(dao: PiDao)
}