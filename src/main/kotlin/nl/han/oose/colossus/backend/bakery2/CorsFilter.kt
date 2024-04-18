package nl.han.oose.colossus.backend.bakery2

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class CorsFilter {

    @Configuration
    @EnableWebMvc
    class WebConfig : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
        }
    }
}