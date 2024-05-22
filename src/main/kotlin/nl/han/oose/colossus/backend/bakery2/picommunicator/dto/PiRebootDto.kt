package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class PiRebootDto {
    private var reboot : Boolean = true

    fun getReboot(): Boolean {
        return this.reboot
    }
    fun setReboot(reboot:Boolean) {
        this.reboot = reboot
    }
}