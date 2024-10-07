package com.signal.domain.post.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CompletionRequestDto {

    private String model;

    private List<Message> messages;

    private float temperature;

    public static CompletionRequestDto toDto(String model, String prompt, float temperature) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("system", "You are a helpful assistant."));
        messages.add(new Message("user", "다음 텍스트에서 누구인지 유추가 가능한 개인정보가 포함된 문장을 그대로 아래 양식에 따라서 대답해줘. isFiltered에는 True, False만 들어가고, InvalidSentences에는 문장들을 List로 묶어서 보내줘. \n\n isFiltered :  \n InvalidSentences : " + prompt));

        return CompletionRequestDto.builder()
            .model(model)
            .messages(messages)
            .temperature(temperature)
            .build();
    }

    public static CompletionRequestDto toDto(String prompt) {
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("user", prompt));

        return CompletionRequestDto.builder()
            .messages(messages)
            .build();
    }

    @Getter
    @Builder
    public static class Message {
        private String role;
        private String content;

        // 메시지 생성자
        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }
    }
}
