package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class PiServiceImp : PiService {
    private val piDao = PiDaoImp()
    override fun getPis(user: Int): PiCollectionDTO {
        val pis = piDao.getPis(user)
        return pis
    }
}