package global.search.pretask.app.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "search-pretask api service",
        version = "v1",
        description = "swagger api interface"
    )
)
@Configuration
class OpenApiConfig {
    @Bean
    fun openAPI(@Value("\${server.servlet.context-path}") contextPath: String): OpenAPI {
        return OpenAPI()
            .addServersItem(Server().url(contextPath))
    }

    @Bean
    fun openApi(): GroupedOpenApi? {
        val path: Array<String> = arrayOf("/v1/**")
        return GroupedOpenApi.builder()
            .group("service-api")
            .pathsToMatch(*path)
            .build()
    }

}