package com.signal.domain.post.controller;

import com.signal.domain.post.dto.request.CompletionRequestDto;
import com.signal.domain.post.service.ChatGPTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "chatGPT", description = "GPT 연결 / 사용")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

//    @Operation(summary = "사용 가능한 모델 리스트 조회")
//    @GetMapping("/modelList")
//    public ResponseEntity<List<Map<String, Object>>> selectModelList() {
//        List<Map<String, Object>> result = chatGPTService.modelList();
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @Operation(summary = "유효한 모델인지 체크")
    @GetMapping("/model")
    public ResponseEntity<Map<String,Object>> isValidModel(
        @RequestParam(name="modelName") String modelName
    ){
        Map<String,Object> result=chatGPTService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "프롬포트 실행")
    @GetMapping("/prompt")
    public ResponseEntity<Map<String,Object>> selectPrompt(
        @RequestBody CompletionRequestDto completionRequestDto
    ){
        Map<String,Object> result=chatGPTService.prompt(completionRequestDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
