package com.signal.domain.post.service;

import com.signal.domain.post.dto.request.CompletionRequestDto;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public interface ChatGPTService {

//    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> prompt(CompletionRequestDto completionRequestDto);
}
