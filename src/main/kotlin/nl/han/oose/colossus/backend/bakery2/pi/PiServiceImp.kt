package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class PiServiceImp : PiService {
    @Autowired
    private lateinit var piDao : PiDao
    override fun getPis(user: Int): PiCollectionDto {
        val pis = piDao.getPis(user)
        return pis    }
}


