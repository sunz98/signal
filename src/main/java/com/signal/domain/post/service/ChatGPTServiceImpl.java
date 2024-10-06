package com.signal.domain.post.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.signal.domain.post.dto.CompletionRequestDto;
import com.signal.global.conig.ChatGPTConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatGPTServiceImpl implements ChatGPTService {

    private final ChatGPTConfig chatGPTConfig;

    @Value("${openai.model}")
    private String model;

    public List<Map<String, Object>> modelList() {
        log.debug("[+] 모델 리스트를 조회합니다.");
        List<Map<String, Object>> resultList = null;

        // 토큰 정보가 포함된 Header를 가져옴
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // 통신을 위한 RestTemplate를 구성, api를 호출
        ResponseEntity<String> response = chatGPTConfig.restTemplate()
            .exchange(
                "<https://api.openai.com/v1/models/>",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
            );

        try {
            // Jackson을 기반으로 응답값을 가져옴
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> data = mapper.readValue(response.getBody(), new TypeReference<>() {});

            // 응답 값을 결과값에 넣고 출력
            resultList = (List<Map<String, Object>>) data.get("data");

            for (Map<String, Object> object : resultList) {
                log.debug("ID: " + object.get("id"));
                log.debug("Object" + object.get("object"));
                log.debug("Created: " + object.get("created"));
                log.debug("Owned By: " + object.get("owned_by"));
            }
        } catch (JsonMappingException e) {
            log.debug("JsonMappingException :: " + e.getMessage());
        } catch (JsonProcessingException e) {
            log.debug("RuntimeException :: " + e.getMessage());
        }

        return resultList;
    }

    @Override
    public Map<String, Object> isValidModel(String modelName) {
        log.debug("[+] 모델이 유효한지 조회합니다. 모델 : " + modelName);
        Map<String, Object> result;

        // 토큰 정보가 포함된 Header를 가져옴
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        // 통신을 위한 RestTemplate 구성
        ResponseEntity<String> response = chatGPTConfig.restTemplate()
            .exchange(
                "<https://api.openai.com/v1/modes/>" + modelName,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
            );

        try {
            // Jackson 기반으로 응답값 가져오기
            ObjectMapper mapper = new ObjectMapper();
            result = mapper.readValue(response.getBody(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessingException :: " + e.getMessage());
        }

        return result;
    }



    @Override
    public Map<String,Object> prompt(CompletionRequestDto completionRequestDto){
        log.debug("[+] 프롬프트를 수행합니다");

        Map<String ,Object> result = new HashMap<>();

        // 토큰 정보가 포함된 Header를 가져옵니다
        HttpHeaders headers = chatGPTConfig.httpHeaders();
        String requestBody = "";
        ObjectMapper om = new ObjectMapper();

        //properties의 model을 가져와서 객체를 추가
        completionRequestDto = CompletionRequestDto.toDto(model, completionRequestDto.getPrompt(), 0.8f);

        try { //Object -> String 직렬화를 구성
            requestBody =om.writeValueAsString(completionRequestDto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        // 통신을 위한 RestTempleate을 구성
        HttpEntity<String> requestEntity = new HttpEntity(completionRequestDto, headers);
        ResponseEntity<String> response =chatGPTConfig.restTemplate()
            .exchange(
                "<https://api.openai.com/v1/completions>",
                HttpMethod.POST,
                requestEntity,
                String.class
            );

        try{
            // String -> HashMap 역직렬화를 구성
            result = om.readValue(response.getBody(), new TypeReference<>(){});
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
