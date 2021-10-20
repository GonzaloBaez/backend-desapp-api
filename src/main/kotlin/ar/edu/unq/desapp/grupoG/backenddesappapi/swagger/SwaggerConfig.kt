package ar.edu.unq.desapp.grupoG.backenddesappapi.swagger

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.w3c.dom.DocumentType
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.builders.ApiInfoBuilder

import springfox.documentation.service.ApiInfo
import org.springframework.security.config.annotation.web.builders.WebSecurity
import java.lang.Exception


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api():Docket{
        return Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any()).build().pathMapping("/")
    }
}