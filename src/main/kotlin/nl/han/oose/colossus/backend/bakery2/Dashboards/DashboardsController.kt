package nl.han.oose.colossus.backend.bakery2.Dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardsDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboards")
class DashboardsController {

    var dashboardsService : DashboardsServiceImp = DashboardsServiceImp();

    @GetMapping(produces = ["application/json"])

    fun getAllDashboards(@RequestParam token : String): ResponseEntity<DashboardsDto>{

        var result: DashboardsDto = dashboardsService.getAllDashboards()
        return ResponseEntity<DashboardsDto>(result,HttpStatus.OK)
    }

}