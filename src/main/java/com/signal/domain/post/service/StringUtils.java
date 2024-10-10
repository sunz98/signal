package com.signal.domain.post.service;

public class StringUtils {

    // 제어 문자를 이스케이프 처리하는 유틸리티 메서드
    public static String escapeControlCharacters(String input) {
        if (input == null) return null;
        return input
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\f", "\\f");
    }
}
