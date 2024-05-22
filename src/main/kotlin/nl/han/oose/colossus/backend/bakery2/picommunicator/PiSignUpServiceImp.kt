package nl.han.oose.colossus.backend.bakery2.picommunicator

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class PiSignUpServiceImp : PiSignUpService {

    @Autowired
    private lateinit var piSignUpDao: PiSignUpDao

    override fun createSignUpRequest(macAddress: String, ipAddress: String) {
        piSignUpDao.insertSignUpRequest(macAddress, ipAddress)
    }

    override fun setPiSignUpDao(piSignUpDao: PiSignUpDao) {
        this.piSignUpDao = piSignUpDao
    }

    override fun checkPiExists(macAddress: String): Boolean {
        return this.piSignUpDao.checkPiExists(macAddress)
    }

    override fun checkPiSignUpExists(macAddress: String): Boolean {
        return this.piSignUpDao.checkPiSignUpExists(macAddress)
    }
}