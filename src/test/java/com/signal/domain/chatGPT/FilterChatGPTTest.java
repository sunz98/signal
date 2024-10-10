package com.signal.domain.chatGPT;

import com.signal.domain.post.dto.response.FilterResponse;
import com.signal.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilterChatGPTTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private FilterChatGPTTest filterChatGPTTest;

    private final Random random = new Random();

    // Expanded name lists
    private final List<String> firstNames = List.of(
        "김", "이", "박", "최", "정", "강", "윤", "장", "임", "한",
        "하늘", "슬기", "민재", "서연", "지우", "지훈", "유진",
        "현수", "은정", "수민", "민서", "지혜", "소영", "하린"
    );

    private final List<String> lastNames = List.of(
        "민수", "지혜", "영희", "철수", "수현", "지훈",
        "서연", "동현", "혜진", "준호", "수빈", "유리",
        "영수", "상혁", "진수", "민규", "세영", "도현", "서준"
    );

    private final List<String> casualSentences = List.of(
        "어제 만난 사람 진짜 괜찮더라.",
        "이번 주말에 놀러 가기로 했어.",
        "우리 집 근처에서 커피 마셨어.",
        "하늘이는 요즘 어떻게 지내?",
        "주말에 친구들과 바비큐 파티를 할 계획이야.",
        "슬기랑 같이 영화 보러 가기로 했어.",
        "새로 생긴 레스토랑 가보고 싶어.",
        "친구와 함께 드라이브를 다녀왔어.",
        "공원에서 산책하는 걸 좋아해.",
        "바다에서 서핑을 해보려고 해."
    );

    private final List<String> descriptiveSentences = List.of(
        "그 친구는 성격이 참 밝고 활발했어.",
        "집 근처 카페에서 만나기로 했다.",
        "그는 정말 친절하게 대화하더라.",
        "우리 학교 졸업생이었어.",
        "하늘은 정말 열심히 공부해.",
        "최근에 시작한 운동 덕분에 몸이 가벼워졌어.",
        "그녀의 웃음은 정말 매력적이야.",
        "내 친구는 요리 솜씨가 뛰어나.",
        "우리 동아리 활동이 너무 재미있어.",
        "요즘 일상에 행복을 느끼고 있어."
    );

    private final List<String> informationalSentences = List.of(
        "1990년에 태어난 김민수는 단국대학교를 졸업했어.",
        "박지혜는 삼성전자에서 근무 중이야.",
        "최영희는 구로구에서 거주하고 있어.",
        "하늘은 미술을 전공했어.",
        "요즘 사람들은 만남을 위해 온라인에서도 많이 활동해.",
        "슬기는 소프트웨어 개발자로 일하고 있어.",
        "김지현은 IT 스타트업에서 일하고 있어.",
        "정수현은 대학교에서 심리학을 전공하고 있어.",
        "이유진은 글로벌 기업에서 마케팅 팀에서 일하고 있어.",
        "최민수는 취미로 사진 촬영을 하고 있어."
    );

    private final List<String> personalInfo = List.of(
        "1990년 5월 3일에 태어났고",
        "1994년 9월 12일에 태어났어.",
        "단국대학교 졸업생이야.",
        "서울대학교 다니고 있어.",
        "삼성전자에서 일하고 있어.",
        "구로구 123로에 살고 있어.",
        "강남구에 새로 이사했어.",
        "대전에서 자랐어.",
        "광주에서 유학 중이야.",
        "부산에서 태어났어."
    );

    private String generateRandomSentence(boolean fullName) {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));

        String name;
        if (fullName) {
            name = firstName + getRandomSpaceOrSpecial() + lastName; // Full name with random spaces or special chars
        } else {
            name = firstName + getRandomSpaceOrSpecial() + lastName.charAt(0) + "x"; // Partial name
        }

        // Randomly decide if personal information will be included
        String personalInfoPart = random.nextBoolean() ? personalInfo.get(random.nextInt(personalInfo.size())) : "";

        // Choose a random sentence type: casual, descriptive, or informational
        List<String> sentencePool = randomSentencePool();
        String sentenceTemplate = sentencePool.get(random.nextInt(sentencePool.size()));

        // Randomly place the name and personal info in different parts of the sentence
        int namePosition = random.nextInt(3); // 0: 앞, 1: 중간, 2: 끝
        String sentence;

        if (namePosition == 0) {
            sentence = name + " " + personalInfoPart + sentenceTemplate;
        } else if (namePosition == 1) {
            int index = sentenceTemplate.indexOf("에서");
            if (index != -1) {
                sentence = sentenceTemplate.substring(0, index) + name + " " + personalInfoPart + sentenceTemplate.substring(index);
            } else {
                // Handle the case where "에서" is not found by placing name at the end
                sentence = sentenceTemplate + " " + name + " " + personalInfoPart;
            }
        } else {
            sentence = sentenceTemplate + " " + personalInfoPart + name;
        }

        return sentence;
    }

    private List<String> randomSentencePool() {
        int type = random.nextInt(3);
        if (type == 0) {
            return casualSentences;
        } else if (type == 1) {
            return descriptiveSentences;
        } else {
            return informationalSentences;
        }
    }

    private String getRandomSpaceOrSpecial() {
        String[] options = {"", " ", "-", "_", "."};
        return options[random.nextInt(options.length)];
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
    }

    @Test
    public void testFilterMetrics() {
        // Generate test data
        List<String> fullNames = new ArrayList<>();
        List<String> partialNames = new ArrayList<>();

        int totalTests = 1000; // Generate 100,000 sentences

        for (int i = 0; i < totalTests / 2; i++) {
            fullNames.add(generateRandomSentence(true)); // Full name sentences
            partialNames.add(generateRandomSentence(false)); // Partial name sentences
        }

        // Expected results
        int expectedFilteredFullNames = fullNames.size();  // Expect all full names to be filtered.
        int expectedFilteredPartialNames = partialNames.size(); // Expect all partial names to be filtered.

        // Counters for successful detections
        int correctFilteredFullNames = 0;
        int correctFilteredPartialNames = 0;
        int falsePositives = 0; // Incorrectly identified as filtered

        // Mocking the filterChatGPT method for full names
        for (int i = 0; i < fullNames.size(); i++) {
            String prompt = fullNames.get(i);
            FilterResponse mockResponse = new FilterResponse(true, List.of(prompt)); // Assuming all full names are filtered
            when(postService.filterChatGPT(prompt)).thenReturn(mockResponse);

            // Print current iteration
            System.out.println("Processing full name iteration: " + (i + 1) + " / " + fullNames.size());
        }

        // Test full names
        for (String prompt : fullNames) {
            FilterResponse response = postService.filterChatGPT(prompt);
            if (response.isFiltered()) {
                correctFilteredFullNames++;
            }
        }

        // Mocking the filterChatGPT method for partial names
        for (int i = 0; i < partialNames.size(); i++) {
            String prompt = partialNames.get(i);
            FilterResponse mockResponse = new FilterResponse(true, List.of(prompt)); // Assuming all partial names are filtered
            when(postService.filterChatGPT(prompt)).thenReturn(mockResponse);

            // Print current iteration
            System.out.println("Processing partial name iteration: " + (i + 1) + " / " + partialNames.size());
        }

        // Test partial names
        for (String prompt : partialNames) {
            FilterResponse response = postService.filterChatGPT(prompt);
            if (response.isFiltered()) {
                correctFilteredPartialNames++;
            }
        }

        // Calculate metrics
        double precision = (double) correctFilteredFullNames / (correctFilteredFullNames + falsePositives) * 100; // Precision = TP / (TP + FP)
        double recall = (double) correctFilteredFullNames / expectedFilteredFullNames * 100; // Recall = TP / (TP + FN)
        double f1Score = (2 * precision * recall) / (precision + recall); // F1 Score = 2 * (Precision * Recall) / (Precision + Recall)

        // Accuracy for full and partial names
        double fullNameDetectionAccuracy = (double) correctFilteredFullNames / expectedFilteredFullNames * 100;
        double partialNameDetectionAccuracy = (double) correctFilteredPartialNames / expectedFilteredPartialNames * 100;
        double overallAccuracy = (fullNameDetectionAccuracy + partialNameDetectionAccuracy) / 2;

        // Print results
        System.out.println("Full Name Detection Accuracy: " + fullNameDetectionAccuracy + "%");
        System.out.println("Partial Name Detection Accuracy: " + partialNameDetectionAccuracy + "%");
        System.out.println("Overall Detection Accuracy: " + overallAccuracy + "%");
        System.out.println("Precision: " + precision + "%");
        System.out.println("Recall: " + recall + "%");
        System.out.println("F1 Score: " + f1Score + "%");

        // Assertions
        assertEquals(expectedFilteredFullNames, correctFilteredFullNames);
        assertEquals(expectedFilteredPartialNames, correctFilteredPartialNames);
    }
}
