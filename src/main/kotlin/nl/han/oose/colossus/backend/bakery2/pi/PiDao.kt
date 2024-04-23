package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto

interface PiDao {
    fun getPis(user: Int): PiCollectionDto
    fun setDashboardsNull(dashboardId: Int)

}