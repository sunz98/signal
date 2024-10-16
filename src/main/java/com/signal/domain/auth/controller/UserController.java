package com.signal.domain.auth.controller;

import com.signal.domain.auth.dto.request.ConsultantSignUpRequest;
import com.signal.domain.auth.dto.request.EmailRequest;
import com.signal.domain.auth.dto.request.LoginReqeust;
import com.signal.domain.auth.dto.request.UserPasswordResetRequest;
import com.signal.domain.auth.dto.request.UserSignUpRequest;
import com.signal.domain.auth.dto.request.UserUpdateRequest;
import com.signal.domain.auth.dto.response.FindIdResponse;
import com.signal.domain.auth.dto.response.LoginResponse;
import com.signal.domain.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "로그인/로그아웃/회원가입")
public class UserController {

    private final UserService userService;

    @PostMapping("/user-signup")
    @Operation(summary = "일반 사용자 회원가입")
    public ResponseEntity<String> userSignup (
        @Valid @RequestBody
        UserSignUpRequest userSignUpRequest
    ) {
        return ResponseEntity.ok(userService.userSignup(userSignUpRequest));
    }

//    @GetMapping("/user/my-information")
//    @Operation(summary = "일반 사용자 정보 조회")
//    public ResponseEntity<UserDetailResponse> getUserDetails(
//        @AuthenticationPrincipal CustomUserDetails customUserDetails
//    ) {
//        return ResponseEntity.ok(customUserDetails.getUserDetails());
//    }

    @PostMapping("/consultant-signup")
    @Operation(summary = "전문가 사용자 회원가입")
    public ResponseEntity<String> consultantSignup (
        @Valid @RequestBody
        ConsultantSignUpRequest consultantSignUpRequest
    ) {
        return ResponseEntity.ok(userService.consultantSignup(consultantSignUpRequest));
    }

//    @GetMapping("/consultant/my-information")
//    @Operation(summary = "전문가 사용자 정보 조회")
//    public ResponseEntity<UserDetailResponse> getUserDetails(
//        @AuthenticationPrincipal CustomUserDetails customUserDetails
//    ) {
//        return ResponseEntity.ok(customUserDetails.getUserDetails());
//    }

    @PutMapping("/edit/my-information")
    @Operation(summary = "일반 사용자 회원정보 수정")
    public ResponseEntity<String> editUserInformation (
        @Valid @RequestBody
        UserUpdateRequest userUpdateRequest
//        @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
//        Long userId = customUserDetails.getId();
        return ResponseEntity.ok(userService.editUserInformation(userUpdateRequest, 1L));
    }

    @GetMapping("/find-id")
    @Operation(summary = "id 찾기")
    public ResponseEntity<FindIdResponse> findId(
        @Valid @RequestBody
        EmailRequest emailRequest
    ) {
        return ResponseEntity.ok(userService.findId(emailRequest));
    }

    @PutMapping("/reset-password")
    @Operation(summary = "PW 재설정")
    public ResponseEntity<String> resetPassword (
        @Valid @RequestBody
        UserPasswordResetRequest userPasswordResetRequest
    ) {
        return ResponseEntity.ok(userService.resetPassword(userPasswordResetRequest));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @RequestBody LoginReqeust loginReqeust
    ) {
        LoginResponse loginResponse = userService.login(loginReqeust);
        return ResponseEntity.ok()
            .header("Authorization", "Bearer " + loginResponse.getAccessToken())
            .body(loginResponse);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(
        HttpServletRequest request
    ){
        userService.logout(request);
    }
}
