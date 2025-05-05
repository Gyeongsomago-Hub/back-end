package com.gbsw.gbswhub.domain.ai.model.db;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ChatCompletionDto {

    private String model;

    private List<ChatRequestMsgDto> message;

    @Builder
    public ChatCompletionDto(String model, List<ChatRequestMsgDto> message) {
        this.model = model;
        this.message = message;
    }
}
