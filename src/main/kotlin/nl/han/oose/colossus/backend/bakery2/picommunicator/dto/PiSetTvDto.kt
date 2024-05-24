package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiSetTvDto {
    private var option : Boolean = false

    fun getOption(): Boolean {
        return this.option
    }
    fun setOption(option:Boolean) {
        this.option = option
    }
}