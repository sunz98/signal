package com.signal.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
// 같은 패키지 내 서브 클래스만 호출 가능한 기능 확인했으면 주석 지워도 됨
public class CompletionRequestDto {

    private String model;

    private String prompt;

    private float temperature;

    public static CompletionRequestDto toDto(String model, String prompt, float temperature) {
        return CompletionRequestDto.builder()
            .model(model)
            .prompt(prompt)
            .temperature(temperature)
            .build()
            ;
    }
}
