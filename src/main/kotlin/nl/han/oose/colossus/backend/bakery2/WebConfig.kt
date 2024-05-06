package nl.han.oose.colossus.backend.bakery2

import nl.han.oose.colossus.backend.bakery2.header.CustomHeaderInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var customHeaderInterceptor: CustomHeaderInterceptor

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE")
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(customHeaderInterceptor)
    }
}