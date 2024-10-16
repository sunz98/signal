package com.signal.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String nickname;

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

    private String image;
}
