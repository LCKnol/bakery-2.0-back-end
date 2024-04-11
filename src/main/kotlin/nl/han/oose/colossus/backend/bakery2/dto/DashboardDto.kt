package nl.han.oose.colossus.backend.bakery2.dto

class DashboardDto {

     private var Id: Int


     private var URL: String


     private var name: String


    constructor(Id: Int, URL: String, name: String) {
        this.Id = Id
        this.URL = URL
        this.name = name
    }


    fun setId(newValue: Int) {
        this.Id = newValue // You can still access it within the class
    }

    fun getId(): Int {
        return this.Id // You can still access it within the class
    }

    fun setURL(newValue: String) {
        this.URL = newValue // You can still access it within the class
    }

    fun getURl(): String {
        return this.URL // You can still access it within the class
    }


    fun setName(newValue: String) {
        this.name = newValue // You can still access it within the class
    }

    fun getName(): String {
        return this.name // You can still access it within the class
    }
}