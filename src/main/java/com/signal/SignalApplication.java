package com.signal;

import com.signal.domain.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SignalApplication implements CommandLineRunner {

    private final PostService postService;

    @Autowired
    public SignalApplication(PostService postService) {
        this.postService = postService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SignalApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 애플리케이션 시작 시 동일한 문장 1000번 필터링 작업 수행
        postService.filterSameSentenceMultipleTimes(1000);
    }
}
