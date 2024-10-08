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
        messages.add(new Message("user", "다음 텍스트에서 이름(부분적으로 가려진 이름 포함)과 같은 개인정보가 감지되면, 해당 문장에서 생년월일(예: 1990-01-01), 소속(예: 단국대학교, 삼성전자), 주소(예: 서울특별시 강남구 123로)와 같은 추가적인 개인정보도 함께 필터링해줘. 이름이 부분적으로 가려졌더라도(예: 강x민) 이를 이름으로 간주하고 필터링해. 이름이 감지된 문장은 필터링이 필요해. 필터링 될 문장들은 제일 마지막에 줄거야. 필터링 아래 양식을 json형식으로 보내줘. isFiltered에는 문제가 있는 문장이 있다면 true, 없다면 false가 들어가고, InvalidSentences에는 문제가 있는 문장들을 list로 묶어서 보내줘. \n\n isFiltered : \n InvalidSentences : \n\n" + prompt));

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
