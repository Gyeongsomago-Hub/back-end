package com.gbsw.gbswhub.domain.ai.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbsw.gbswhub.domain.ai.model.Ai;
import com.gbsw.gbswhub.domain.ai.model.db.RequestRoadMapDto;
import com.gbsw.gbswhub.domain.ai.model.repository.AiRepository;
import com.gbsw.gbswhub.domain.global.ChatGptConfig;
import com.gbsw.gbswhub.domain.global.Error.ErrorCode;
import com.gbsw.gbswhub.domain.global.Exception.BusinessException;
import com.gbsw.gbswhub.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gbsw.gbswhub.domain.global.util.UserValidator.validateUser;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    private final ChatGptConfig chatGptConfig;
    private final RestTemplateBuilder restTemplateBuilder;
    private final AiRepository aiRepository;

    public String generatePrompt(RequestRoadMapDto dto) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("너는 학생의 공부를 도와주는 선생님이야. ");
        prompt.append("학생이 공부 스타일과 공부하고 싶은 분야를 입력하면, ");
        prompt.append("네가 학생이 입력한 것을 보고 종합적으로 어떻게 공부해야 할지 자세하게 로드맵으로 방향성을 잡아줘. ");
        prompt.append("학생의 공부 스타일은 '").append(dto.getStduyStyle()).append("'이고, ");
        prompt.append("공부하고 싶은 분야는 '").append(dto.getStduyField()).append("'이야. ");
        prompt.append("학생의 질문에 답변할 때는 친절하고 알기 쉽도록 해줘. ");

        return prompt.toString();
    }

    public String callOpenAI(String prompt, int maxTokens) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(chatGptConfig.getSecretKey());

        Map<String, Object> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", chatGptConfig.getModel());
        System.out.println("현재 사용 중인 GPT 모델: " + chatGptConfig.getModel());
        requestBody.put("messages", List.of(message));
        requestBody.put("max_tokens", maxTokens);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> response = restTemplate.exchange(
                chatGptConfig.getApiUrl(),
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    public ResponseEntity<Map<String, String>> generateRoadmap(RequestRoadMapDto dto, User user) {
        validateUser(user);

        String prompt = generatePrompt(dto);

        try {
            String rawJson = callOpenAI(prompt, 1000);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(rawJson);
            String content = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 저장
            Ai ai = Ai.builder()
                    .user(user)
                    .question(dto.getStduyStyle() + " " + dto.getStduyField())
                    .answer(content) // 진짜 답변만 저장
                    .build();
            aiRepository.save(ai);

            // 응답
            Map<String, String> response = new HashMap<>();
            response.put("roadmap", content);
            return ResponseEntity.ok(response);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
