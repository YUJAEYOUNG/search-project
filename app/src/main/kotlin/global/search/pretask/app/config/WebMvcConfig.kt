package global.search.pretask.app.config

import org.springframework.boot.convert.ApplicationConversionService
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@EnableWebMvc
@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        ApplicationConversionService.configure(registry)
    }
}