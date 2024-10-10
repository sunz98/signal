package com.signal.domain.post.model;

public class FilteringResult {
    private String content;
    private boolean actualLabel;  // 실제 라벨 (적합/부적합)
    private boolean filtered;     // ChatGPT 필터링 결과 (필터링 여부)

    public FilteringResult(String content, boolean actualLabel, boolean filtered) {
        this.content = content;
        this.actualLabel = actualLabel;
        this.filtered = filtered;
    }

    public boolean isActualLabel() {
        return actualLabel;
    }

    public boolean isFiltered() {
        return filtered;
    }
}
