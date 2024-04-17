package nl.han.oose.colossus.backend.bakery2.dto

class DashboardDto {

     private var id: Int


     private var url: String


     private var name: String

     private var imageUrl: String


    constructor(id: Int, url: String, name: String,imageUrl: String) {
        this.id = id
        this.url = url
        this.name = name
        this.imageUrl = imageUrl
    }


    fun setId(newValue: Int) {
        this.id = newValue // You can still access it within the class
    }

    fun getId(): Int {
        return this.id // You can still access it within the class
    }

    fun setURL(newValue: String) {
        this.url = newValue // You can still access it within the class
    }

    fun getURl(): String {
        return this.url // You can still access it within the class
    }

    fun setImageURL(newValue: String) {
        this.imageUrl = newValue // You can still access it within the class
    }

    fun getImageURL(): String {
        return this.imageUrl // You can still access it within the class
    }

    fun setName(newValue: String) {
        this.name = newValue // You can still access it within the class
    }

    fun getName(): String {
        return this.name // You can still access it within the class
    }
}