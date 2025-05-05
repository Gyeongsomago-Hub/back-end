package com.gbsw.gbswhub.domain.global;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ChatGptConfig {

    @Value("${spring.ai.openai.api-key}")
    private String secretKey;

    @Value("${spring.ai.openai.chat.model}")
    private String model;

    @Value("${spring.ai.openai.base-url}")
    private String apiUrl;
}