package com.signal.domain.post.controller;

import com.signal.domain.post.dto.CompletionRequestDto;
import com.signal.domain.post.service.ChatGPTService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chatGPT")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    // ChatGPT 사용 가능한 모델 리스트 조회
    @GetMapping("/modelList")
    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
        List<Map<String, Object>> result = chatGPTService.modelList();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 유효한 모델인지 체크
    @GetMapping("/model")
    public ResponseEntity<Map<String,Object>> isValidModel(
        @RequestParam(name="modelName") String modelName
    ){
        Map<String,Object> result=chatGPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 모델을 이용하여 프롬포트 호출
    @GetMapping("/prompt")
    public ResponseEntity<Map<String,Object>> selectPrompt(
        @RequestBody CompletionRequestDto completionRequestDto
    ){
        Map<String,Object> result=chatGPTService.prompt(completionRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
