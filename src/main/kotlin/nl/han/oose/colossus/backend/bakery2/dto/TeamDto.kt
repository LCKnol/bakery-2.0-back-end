package nl.han.oose.colossus.backend.bakery2.dto

class TeamDto {
    private lateinit var name: String
    private var id: Int = 0
    fun getName(): String {return this.name}
    fun setName(name: String) {this.name = name}
    fun getId(): Int {return this.id}
    fun setId(id: Int) {this.id = id}
}