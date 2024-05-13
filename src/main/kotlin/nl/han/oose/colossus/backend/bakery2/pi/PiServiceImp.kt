package nl.han.oose.colossus.backend.bakery2.pi

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class PiServiceImp : PiService {
    @Autowired
    private lateinit var piDao: PiDao

    override fun getPis(user: Int): PiCollectionDto {
        val pis = piDao.getPis(user)
        return pis
    }

    override fun setPiDao(dao: PiDao) {
        piDao = dao
    }

    override fun getAllPis(): PiCollectionDto {
        return piDao.getAllPis()
    }

    override fun getAllPiRequests(): PiRequestsCollectionDto {
        return piDao.getAllPiRequests()
    }

    override fun addPi(macAddress: String, name: String, roomno: String) {
        piDao.insertPi(macAddress, name, roomno)
        deletePiRequest(macAddress)

    }

    override fun declinePiRequest(macAddress: String) {
        deletePiRequest(macAddress)
    }

    private fun deletePiRequest(macAddress: String) {
        piDao.deletePiRequest(macAddress)
    }

    override fun editPi(piDto: PiDto, userId: Int) {

       piDao.editPi(piDto)
    }

    override fun getPi(piId: Int): PiDto {
        val pi = piDao.getPi(piId) ?: throw HttpNotFoundException("pi does not exist")
        return pi
    }
}