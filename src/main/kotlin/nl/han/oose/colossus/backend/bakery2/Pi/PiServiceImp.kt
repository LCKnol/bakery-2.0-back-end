package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserDao
import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class PiServiceImp @Autowired constructor(
        private var piDao: PiDao) : PiService {
    override fun getPis(user: Int): PiCollectionDTO {
        val pis = piDao.getPis(user)
        return pis
    }
}