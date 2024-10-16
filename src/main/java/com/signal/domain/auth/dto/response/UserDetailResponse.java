package com.signal.domain.auth.dto.response;

import com.signal.domain.auth.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailResponse {

    private Long id;
    private String image;
    private String userId;
    private String email;
    private String nickname;
    private Gender gender;
}
