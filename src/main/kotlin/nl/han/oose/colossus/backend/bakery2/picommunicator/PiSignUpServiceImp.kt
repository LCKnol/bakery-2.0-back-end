package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class PiSignUpServiceImp : PiSignUpService {

    @Autowired
    private lateinit var piSignUpDao: PiSignUpDao

    override fun createSignUpRequest(macAddress: String) {
        piSignUpDao.insertSignUpRequest(macAddress)
    }

    override fun setPiSignUpDao(piSignUpDao: PiSignUpDao) {
        this.piSignUpDao = piSignUpDao
    }

    override fun checkPiExists(macAddress: String): Boolean {
        return this.piSignUpDao.checkPiExists(macAddress)
    }

    override fun checkPiSignUpExists(macAddress: String) : Boolean {
        return this.piSignUpDao.checkPiSignUpExists(macAddress)
    }

}