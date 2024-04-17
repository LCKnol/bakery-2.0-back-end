package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserDao
import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Primary
@Component
class PiServiceImp : PiService {
    @Autowired
    private lateinit var piDao : PiDao
    override fun getPis(user: Int): PiCollectionDTO {
        val pis = piDao.getPis(user)
        return pis    }
}


