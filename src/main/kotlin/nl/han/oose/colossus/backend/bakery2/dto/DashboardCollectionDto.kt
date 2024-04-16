package nl.han.oose.colossus.backend.bakery2.dto

class DashboardCollectionDto {

    private var dashboards:ArrayList<DashboardDto> = ArrayList()


    fun setDashboards(newValue: ArrayList<DashboardDto>) {
        this.dashboards = newValue // You can still access it within the class
    }

    fun getDashboards(): ArrayList<DashboardDto> {
        return this.dashboards // You can still access it within the class
    }
}