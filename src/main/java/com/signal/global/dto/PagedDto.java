package com.signal.global.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagedDto<T> {

    private int totalPages; // 전체 페이지 수

    private int pageSize; // 한 페이지에 포함된 데이터 수

    private int currentPage; // 현 페이지 번호

    private List<T> contents;

    public static <T> PagedDto<T> toDTO(
        int currentPage, int size, int totalCount, List<T> contents) {
        return PagedDto.<T>builder()
            .currentPage(currentPage)
            .pageSize(size)
            .totalPages(totalCount)
            .contents(contents)
            .build();
    }

    public static <T> PagedDto<T> toDTO(Page<T> page) {
        return PagedDto.<T>builder()
            .totalPages(page.getTotalPages())
            .pageSize(page.getSize())
            .currentPage(page.getNumber())
            .contents(page.getContent())
            .build();
    }
}
