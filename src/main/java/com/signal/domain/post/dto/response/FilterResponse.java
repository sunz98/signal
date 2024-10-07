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

    private boolean isFiltered;
    private List<String> invalidSentences;

    public static FilterResponse toDto(boolean isFiltered, List<String> invalidSentences) {
        return FilterResponse.builder()
            .isFiltered(isFiltered)
            .invalidSentences(invalidSentences)
            .build()
            ;
    }
}
