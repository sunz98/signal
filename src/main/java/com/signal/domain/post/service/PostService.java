package com.signal.domain.post.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.signal.domain.auth.model.User;
import com.signal.domain.auth.repository.UserRepository;
import com.signal.domain.post.dto.request.CompletionRequestDto;
import com.signal.domain.post.dto.request.PostRequest;
import com.signal.domain.post.dto.response.FilterResponse;
import com.signal.domain.post.dto.response.PostDetailResponse;
import com.signal.domain.post.dto.response.PostResponse;
import com.signal.domain.post.dto.response.SearchResponse;
import com.signal.domain.post.model.Post;
import com.signal.domain.post.model.enums.Category;
import com.signal.domain.post.repository.PostRepository;
import com.signal.global.dto.PagedDto;
import com.signal.global.exception.errorCode.ErrorCode;
import com.signal.global.exception.handler.InvalidValueException;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ChatGPTServiceImpl chatGPTService;

    @Transactional
    public PagedDto<SearchResponse> getPosts(
        Category category, int size, int page
    ) {
        Post hotpost = postRepository.findTopByOrderByViewCountDesc(category);
        PostResponse hotpostResponse = PostResponse.toDto(hotpost);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Post> posts = postRepository.findByCategory(category, pageRequest);

        List<PostResponse> postsResponse = posts.stream()
            .map(
                PostResponse::toDto
            ).collect(Collectors.toList());

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

    public String createPost(PostRequest postRequest, Long userId) {

        // userId  검증필요, 임시 User 생성
        // consultant가 아닌지도 확인 필요 추후 수정해야 함.
        User user = userRepository.findUserById(userId);

        FilterResponse filterResponse = filterChatGPT(postRequest.getTitle() + " " + postRequest.getContents());

        log.info("isFiltered: {}", filterResponse.isFiltered());

        if(filterResponse.isFiltered()) {
            String invalidSentences = String.join("/ ", filterResponse.getInvalidSentences());
            return "Denied: " + invalidSentences;
        }

        Post newPost = Post.toEntity(postRequest, user);
        postRepository.save(newPost);

        return "Post created";
    }

    public FilterResponse filterChatGPT(String prompt) {

        CompletionRequestDto completionRequestDto = CompletionRequestDto.toDto(prompt);
        Map<String, Object> result = chatGPTService.prompt(completionRequestDto);

        List<String> invalidSentences = new ArrayList<>();
        String responseText = "";

        log.info("ChatGPT Result: {}", result);

        // 답변에 무조건 choices가 포함됨
        if (result.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) result.get("choices");

            Map<String, Object> firstChoice = (Map<String, Object>) choices.get(0);
            Map<String, Object> message = (Map<String, Object>) firstChoice.get("message");

            responseText = (String) message.get("content");

            // isFiltered와 invalidSentences 추출
            String[] lines = responseText.split("\n");
            boolean isFiltered = false;

            for (String line : lines) {
                if (line.startsWith("isFiltered :")) {
                    // 정확한 문자열 비교로 수정
                    isFiltered = line.trim().equals("isFiltered : True");
                } else if (line.startsWith("InvalidSentences :")) {
                    String sentences = line.substring(line.indexOf(":") + 1).trim();
                    if (!sentences.equals("[]")) { // 빈 리스트가 아닐 경우
                        invalidSentences = Arrays.asList(sentences.replaceAll("[\\[\\]\"]", "").split(","));
                    }
                }
            }

            return FilterResponse.toDto(isFiltered, invalidSentences);
        }

        return FilterResponse.toDto(false, invalidSentences);
    }
}
