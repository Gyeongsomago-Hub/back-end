package com.gbsw.gbswhub.domain.global;

import com.gbsw.gbswhub.domain.global.Error.ErrorResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        String jwtSchemeName = "JWT";
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name("Authorization")
                )
                .addSchemas("ErrorResponse", new Schema<ErrorResponse>()
                        .addProperty("message", new Schema<String>())
                        .addProperty("status", new Schema<Integer>())
                )
                .addResponses("200", createErrorResponse("회원가입이 완료되었습니다.", 200))
                .addResponses("400", createErrorResponse("잘못된 입력값입니다.", 400))
                .addResponses("401", createErrorResponse("비밀번호가 일치하지 않습니다.", 401))
                .addResponses("404", createErrorResponse("사용자가 존재하지 않습니다.", 404))
                .addResponses("409", createErrorResponse("이미 사용 중인 아이디입니다.", 409))
                .addResponses("500", createErrorResponse("서버 내부 오류가 발생했습니다.", 500));

        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(securityRequirement)
                .components(components);
    }

    private Info apiInfo() {
        return new Info()
                .title("경소마고 Hub API")
                .description("경소마고 Hub와 관련된 API들을 다루는 문서입니다.")
                .version("1.0.0");
    }

    private ApiResponse createErrorResponse(String message, int status) {
        return new ApiResponse()
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<ErrorResponse>()
                                .addProperty("message", new Schema<String>().example(message))
                                .addProperty("status", new Schema<Integer>().example(status))
                        )));
    }

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            String path = handlerMethod.getMethodAnnotation(org.springframework.web.bind.annotation.RequestMapping.class) != null
                    ? handlerMethod.getMethodAnnotation(org.springframework.web.bind.annotation.RequestMapping.class).value()[0]
                    : "";
            if (path.startsWith("/api/login/**") || path.equals("/api/signup")) {
                operation.setSecurity(new java.util.ArrayList<>());
            }
            return operation;
        };
    }
}
