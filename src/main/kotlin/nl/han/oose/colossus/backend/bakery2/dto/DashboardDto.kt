package nl.han.oose.colossus.backend.bakery2.dto

class DashboardDto {

    private var id: Int

    private var dashboardUrl: String

    private var dashboardName: String

//    private var imageUrl: String

    private var dashboardRefresh: Int

    private var team: TeamDto

    private var hasAccess: Boolean

    constructor(
        id: Int,
        dashboardUrl: String,
        dashboardName: String,
//        imageUrl: String,
        dashboardRefresh: Int,
        team: TeamDto,
        hasAccess: Boolean
    ) {
        this.id = id
        this.dashboardUrl = dashboardUrl
        this.dashboardName = dashboardName
//        this.imageUrl = imageUrl
        this.dashboardRefresh = dashboardRefresh
        this.team = team
        this.hasAccess = hasAccess
    }

    fun setId(newValue: Int) {
        this.id = newValue // You can still access it within the class
    }

    fun getId(): Int {
        return this.id // You can still access it within the class
    }

    fun getTeam(): TeamDto {
        return this.team // You can still access it within the class
    }

    fun setTeam(newValue: TeamDto) {
        this.team = newValue // You can still access it within the class
    }

    fun setDashboardUrl(newValue: String) {
        this.dashboardUrl = newValue // You can still access it within the class
    }

    fun getDashboardUrl(): String {
        return this.dashboardUrl // You can still access it within the class
    }

//    fun setImageUrl(newValue: String) {
//        this.imageUrl = newValue // You can still access it within the class
//    }
//
//    fun getImageUrl(): String {
//        return this.imageUrl // You can still access it within the class
//    }

    fun setDashboardName(newValue: String) {
        this.dashboardName = newValue // You can still access it within the class
    }

    fun getDashboardName(): String {
        return this.dashboardName // You can still access it within the class
    }

    fun setDashboardRefresh(newValue: Int) {
        this.dashboardRefresh = newValue
    }

    fun getDashboardRefresh(): Int {
        return this.dashboardRefresh
    }

    fun setHasAccess(newValue: Boolean) {
        this.hasAccess = newValue
    }

    fun getHasAccess(): Boolean {
        return this.hasAccess
    }
}
