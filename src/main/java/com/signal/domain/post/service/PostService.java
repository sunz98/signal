package com.signal.domain.post.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signal.domain.auth.model.User;
import com.signal.domain.auth.repository.UserRepository;
import com.signal.domain.post.dto.request.CompletionRequestDto;
import com.signal.domain.post.dto.request.PostRequest;
import com.signal.domain.post.dto.response.FilterResponse;
import com.signal.domain.post.dto.response.PostDetailResponse;
import com.signal.domain.post.dto.response.PostResponse;
import com.signal.domain.post.dto.response.SearchResponse;
import com.signal.domain.post.model.FilteringResult;

import com.signal.domain.post.model.Post;
import com.signal.domain.post.model.enums.Category;
import com.signal.domain.post.repository.PostRepository;
import com.signal.global.dto.PagedDto;
import com.signal.global.exception.errorCode.ErrorCode;
import com.signal.global.exception.handler.InvalidValueException;
import io.swagger.v3.core.util.Json;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Filter;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatGPTServiceImpl chatGPTService;

    // 필터링 결과를 저장할 리스트 (메모리 상에 유지)
    private final List<FilteringResult> filteringResults = new ArrayList<>();

    @Transactional
    public PagedDto<SearchResponse> getPosts(Category category, int size, int page) {
        Post hotpost = postRepository.findTopByOrderByViewCountDesc(category);
        PostResponse hotpostResponse = PostResponse.toDto(hotpost);

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByCategory(category, pageRequest);

        List<PostResponse> postsResponse = posts.stream()
            .map(PostResponse::toDto)
            .collect(Collectors.toList());

        int totalCount = (int) posts.getTotalElements();
        SearchResponse searchResponse = SearchResponse.toDto(totalCount, hotpostResponse, postsResponse);

        return PagedDto.toDTO(page, size, posts.getTotalPages(), List.of(searchResponse));
    }

    @Transactional
    public PostDetailResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new InvalidValueException(ErrorCode.POST_NOT_FOUND));

        return PostDetailResponse.toDto(post);
    }

    public FilterResponse createPost(PostRequest postRequest, Long userId) {
        // 유저 검증
        User user = userRepository.findUserById(userId);

        // 필터링 수행
        FilterResponse filterResponse = chatGPTService.filterChatGPT(postRequest.getTitle() + " " + postRequest.getContents());

        // 실제 라벨은 비즈니스 로직에 따라 결정
        boolean actualLabel = validatePost(postRequest);  // true: 적합, false: 부적합

        // 필터링 결과 저장
        FilteringResult result = new FilteringResult(postRequest.getTitle(), actualLabel, filterResponse.isFiltered());
        filteringResults.add(result);

        // 콘솔에 결과 출력
        log.info("Actual Label: {}, Filtered: {}", actualLabel, filterResponse.isFiltered());

        // 필터링 통과한 경우만 저장
        if (!filterResponse.isFiltered()) {
            Post newPost = Post.toEntity(postRequest, user);
            postRepository.save(newPost);
        }

        return filterResponse;
    }

    // 필터링 정확도 평가 메서드 (콘솔 출력)
    public void evaluateFilteringAccuracy() {
        int TP = 0, TN = 0, FP = 0, FN = 0;

        for (FilteringResult result : filteringResults) {
            boolean actual = result.isActualLabel();
            boolean predicted = result.isFiltered();

            if (actual && predicted) TP++;  // True Positive
            else if (!actual && !predicted) TN++;  // True Negative
            else if (!actual && predicted) FP++;  // False Positive
            else if (actual && !predicted) FN++;  // False Negative
        }

        // 정확도, 정밀도, 재현율, F1 스코어 계산
        double accuracy = (TP + TN) / (double) (TP + TN + FP + FN);
        double precision = (TP + FP) > 0 ? TP / (double) (TP + FP) : 0.0;
        double recall = (TP + FN) > 0 ? TP / (double) (TP + FN) : 0.0;
        double f1Score = (precision + recall) > 0 ? 2 * ((precision * recall) / (precision + recall)) : 0.0;

        // 콘솔에 결과 출력
        log.info("Accuracy: {}", accuracy);
        log.info("Precision: {}", precision);
        log.info("Recall: {}", recall);
        log.info("F1 Score: {}", f1Score);
    }

    // 실제 적합/부적합 여부를 결정하는 메서드 (임시로 설정)
    private boolean validatePost(PostRequest postRequest) {
        // 실제 비즈니스 로직에 따라 적절히 수정
        return !postRequest.getContents().contains("부적절");
    }

    // 동일한 문장을 반복해서 필터링하고 정확도 평가
    public void filterSameSentenceMultipleTimes(int repeatCount) {
        String testSentence = "남자친구랑 300일이 막 지났습니다. 남자친구가 단국대생 소웨과 강현민인데...";

        // 임시 사용자 ID, 필요에 따라 수정
        Long testUserId = 1L;

        // 동일한 문장을 반복해서 필터링 수행
        for (int i = 0; i < repeatCount; i++) {
            PostRequest postRequest = new PostRequest();
            postRequest.setTitle("테스트 제목");
            postRequest.setContents(testSentence);
            postRequest.setCategory(Category.valueOf("_10S")); // 필요에 따라 카테고리 설정

            // 필터링 수행
            FilterResponse filterResponse = createPost(postRequest, testUserId);

            // 로그로 진행 상황 출력
            log.info("Iteration {}: Filtered = {}", i + 1, filterResponse.isFiltered());
        }

        // 1000번 필터링 후 정확도 평가
        evaluateFilteringAccuracy();
    }

}

