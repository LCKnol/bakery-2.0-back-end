package nl.han.oose.colossus.backend.bakery2.dto

class PiCollectionDTO {
    private var piCollection: ArrayList<PiDTO> = ArrayList()

    fun getPis(): ArrayList<PiDTO> {
        return piCollection
    }

    fun setPis(newPis: List<PiDTO>) {
        piCollection.clear()
        piCollection.addAll(newPis)
    }
}
