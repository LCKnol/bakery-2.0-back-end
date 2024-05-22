package nl.han.oose.colossus.backend.bakery2.picommunicator.dto

class SocketResponseDto {
    private lateinit var instruction: String
    private var body: Any? = null

    fun getInstruction(): String {
        return instruction
    }

    fun setInstruction(instruction: String) {
        this.instruction = instruction
    }

    fun setBody(body: Any) {
        this.body = body
    }

    fun getBody(): Any? {
        return body
    }
}