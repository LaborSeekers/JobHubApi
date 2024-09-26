package thelaborseekers.jobhubapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerAPIConfig {

    @Value("${JobHubApi.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI(){

        //dev server definition
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("JobHub API development server");

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
        return new OpenAPI().info(info).addServersItem(devServer);

    }

}
