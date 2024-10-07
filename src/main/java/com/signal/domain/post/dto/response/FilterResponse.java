package com.signal.domain.post.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterResponse {

    private boolean isValid;
    private List<String> invalidSentences;
    private String message;

    public static FilterResponse toDto(boolean isValid, List<String> invalidSentences, String message) {
        return FilterResponse.builder()
            .isValid(isValid)
            .invalidSentences(invalidSentences)
            .message(message)
            .build()
            ;
    }
}
