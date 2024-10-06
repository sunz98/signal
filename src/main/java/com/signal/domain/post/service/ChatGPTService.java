package com.signal.domain.post.service;

import com.signal.domain.post.dto.CompletionRequestDto;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public interface ChatGPTService {

    List<Map<String, Object>> modelList();

    Map<String, Object> isValidModel(String modelName);

    Map<String, Object> prompt(CompletionRequestDto completionRequestDto);
}
