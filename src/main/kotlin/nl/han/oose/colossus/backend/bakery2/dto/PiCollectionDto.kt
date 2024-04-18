package nl.han.oose.colossus.backend.bakery2.dto

class PiCollectionDto {
    private var piCollection: ArrayList<PiDto> = ArrayList()

    fun getPis(): ArrayList<PiDto> {
        return piCollection
    }

    fun setPis(newPis: List<PiDto>) {
        piCollection.clear()
        piCollection.addAll(newPis)
    }
}
