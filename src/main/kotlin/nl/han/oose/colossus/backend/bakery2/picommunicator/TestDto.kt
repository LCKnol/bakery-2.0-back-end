package nl.han.oose.colossus.backend.bakery2.picommunicator

class TestDto {
    private lateinit var instruction: String
    private lateinit var body: List<String>

    fun getInstruction(): String {
        return instruction
    }

    fun setInstruction(instruction: String) {
        this.instruction = instruction
    }

    fun setBody(body: List<String>) {
        this.body = body
    }

    fun getBody(): List<String> {
        return body
    }
}