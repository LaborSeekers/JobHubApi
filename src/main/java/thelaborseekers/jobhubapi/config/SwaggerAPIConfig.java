package thelaborseekers.jobhubapi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;

import java.util.List;

@Configuration
public class SwaggerAPIConfig {

    @Value("${JobHubApi.openapi.dev-url}")
    private String devUrl;

    @Value("${JobHubApi.openapi.prod-url}")
    private String prodUrl;
    @Bean
    public OpenAPI myOpenAPI(){

        //dev server definition
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("JobHub API development server");

        //prod server definition
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("JobHub API Production server");

        //contact info  1/5
        Contact contact = new Contact();
        contact.setEmail("U201816862@upc.edu.pe");
        contact.setName("Karim Samanamud");
        contact.setUrl("https://github.com/KarimS21");

        License license = new License().name("MIT License").url("https://opensource.org/licenses/MIT");

        //general API info
        Info info = new Info()
                .title("JobHub API - Bolsa de Trabajo")
                .version("1.0")
                .contact(contact)
                .description("La plataforma de busqueda de empleo y de talento definitiva")
                .termsOfService("")
                .license(license);

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("JWT Authentication");

        Components components = new Components()
                .addSecuritySchemes("bearerAuth", securityScheme);

        // Requerimiento de seguridad para utilizar en las operaciones
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI().info(info).servers(List.of(devServer,prodServer)).addSecurityItem(securityRequirement).components(components);
    }

}
