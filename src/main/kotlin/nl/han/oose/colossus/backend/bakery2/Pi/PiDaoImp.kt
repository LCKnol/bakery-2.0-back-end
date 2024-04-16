package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import nl.han.oose.colossus.backend.bakery2.dto.PiDTO

class PiDaoImp : PiDao {
    override fun getPis(user: Int): PiCollectionDTO {
        var pi1 = PiDTO()
        var pi2 = PiDTO()
        pi1.setDisplay("cool dashboard")
        pi1.setName("12-12-12")
        pi1.setStatus("online")
        pi2.setDisplay("lame dashboard")
        pi2.setName("12-12-11")
        pi2.setStatus("offline")
        var pis = PiCollectionDTO()
        pis.setPis(arrayListOf( pi1, pi2))
        return pis
    }
}