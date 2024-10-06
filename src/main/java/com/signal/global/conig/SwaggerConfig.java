package com.signal.global.conig;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Signal 명세서",
            description = "Signal",
            version = "v1")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .components(new Components());
    }

//    private static final String SECURITY_SCHEME_NAME = "JWT";
//
//    @Bean
//    public ModelResolver modelResolver(ObjectMapper objectMapper) {
//        return new ModelResolver(objectMapper);
//    }
//
//    @Bean
//    public OpenAPI openAPI() {
//        return new OpenAPI()
//            .addSecurityItem(createSecurityRequirement())
////            .components(createSecurityComponents())
//            .components(new Components());
//    }
//
//    private SecurityRequirement createSecurityRequirement() {
//        return new SecurityRequirement().addList(SECURITY_SCHEME_NAME);
//    }
//
//    private Components createSecurityComponents() {
//        return new Components()
//            .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
//                .name(SECURITY_SCHEME_NAME)
//                .type(SecurityScheme.Type.HTTP)
//                .scheme("Bearer")
//                .bearerFormat(SECURITY_SCHEME_NAME));
//    }
//
//    private RequestBody createLoginRequestBody() {
//        return new RequestBody()
//            .content(
//                new Content()
//                    .addMediaType(
//                        "application/json",
//                        new MediaType()
//                            .schema(
//                                new Schema<>()
//                                    .type("object")
//                                    .properties(
//                                        Map.of(
//                                            "email",
//                                            new Schema<>()
//                                                .type(
//                                                    "string"),
//                                            "password",
//                                            new Schema<>()
//                                                .type(
//                                                    "string"))))));
//    }
//
//    private ApiResponses createLoginResponses() {
//        return new ApiResponses()
//            .addApiResponse("200", new ApiResponse().description("Successful login"))
//            .addApiResponse("400", new ApiResponse().description("Bad request"));
//    }
}
