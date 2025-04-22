package com.gbsw.gbswhub.domain.global;

import com.gbsw.gbswhub.domain.global.Error.ErrorResponse;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
                .addResponses("204", createErrorResponse("삭제 성공", 204))
                .addResponses("Project200", createErrorResponse("프로젝트 모집이 생성되었습니다.", 200))
                .addResponses("Mentoring200", createErrorResponse("멘토멘티 모집이 생성되었습니다.", 200))
                .addResponses("Category200", createErrorResponse("카테고리가 생성되었습니다.", 200))
                .addResponses("RequestProject200", createErrorResponse("프로젝트 모집에 참가 완료되었습니다.", 200))
                .addResponses("RequestMentoring200", createErrorResponse("멘토멘티 모집에 참가 신청되었습니다.", 200))
                .addResponses("Club200", createErrorResponse("동아리 모집 공고가 생성되었습니다.", 200))
                .addResponses("400", createErrorResponse("잘못된 입력값입니다.", 400))
                .addResponses("401", createErrorResponse("비밀번호가 일치하지 않습니다.", 401))
                .addResponses("403", createErrorResponse("접근 권한이 없습니다.", 403))
                .addResponses("Login401", createErrorResponse("유효하지 않은 토큰입니다.", 401))
                .addResponses("Category404", createErrorResponse("카테고리를 찾을 수 없습니다.", 404))
                .addResponses("Project404", createErrorResponse("프로젝트 모집을 찾을 수 없습니다.", 404))
                .addResponses("Mentoring404", createErrorResponse("멘토멘티 모집을 찾을 수 없습니다.", 404))
                .addResponses("RequestMentoring404", createErrorResponse("멘토멘티 신청을 찾을 수 없습니다.", 404))
                .addResponses("Club404", createErrorResponse("동아리 모집 공고를 찾을 수 없습니다.", 404))
                .addResponses("404", createErrorResponse("사용자를 찾을 수 없습니다.", 404))
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
}
