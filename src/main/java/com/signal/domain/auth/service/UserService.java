package com.signal.domain.auth.service;

import com.signal.domain.auth.dto.request.ConsultantSignUpRequest;
import com.signal.domain.auth.dto.request.EmailRequest;
import com.signal.domain.auth.dto.request.LoginReqeust;
import com.signal.domain.auth.dto.request.UserPasswordResetRequest;
import com.signal.domain.auth.dto.request.UserSignUpRequest;
import com.signal.domain.auth.dto.request.UserUpdateRequest;
import com.signal.domain.auth.dto.response.FindIdResponse;
import com.signal.domain.auth.dto.response.LoginResponse;
import com.signal.domain.auth.model.User;
import com.signal.domain.auth.model.enums.Role;
import com.signal.domain.auth.repository.AuthRepository;
import com.signal.global.exception.errorCode.ErrorCode;
import com.signal.global.exception.handler.EntityNotFoundException;
import com.signal.global.exception.handler.InvalidValueException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final AuthRepository authRepository;
    private final EmailService emailService;
    //TODO : SecurityConfig 만들어지면 @EnableWebSecuriy 어노테이션 있을 경우 에러 해결됨
    private final PasswordEncoder passwordEncoder;

    public String userSignup(UserSignUpRequest userSignUpRequest) {
        authRepository.existsUserByEmail(userSignUpRequest.getEmail());

        emailService.isEmailVerified(userSignUpRequest.getEmail());

        User user = User.builder()
            .userId(userSignUpRequest.getUserId())
            .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
            .nickname(userSignUpRequest.getNickname())
            .birthday(userSignUpRequest.getBirthday())
            .gender(userSignUpRequest.getGender())
            .email(userSignUpRequest.getEmail())
            .role(Role.USER)
            .createdAt(LocalDateTime.now())
            .build()
            ;

        authRepository.save(user);

        return "User Registered Successfully";
    }

    public String consultantSignup(ConsultantSignUpRequest consultantSignUpRequest) {
        authRepository.existsUserByEmail(consultantSignUpRequest.getEmail());

        emailService.isEmailVerified(consultantSignUpRequest.getEmail());

        User user = User.builder()
            .userId(consultantSignUpRequest.getUserId())
            .password(passwordEncoder.encode(consultantSignUpRequest.getPassword()))
            .nickname(consultantSignUpRequest.getNickname())
            .birthday(consultantSignUpRequest.getBirthday())
            .gender(consultantSignUpRequest.getGender())
            .email(consultantSignUpRequest.getEmail())
            .image(consultantSignUpRequest.getImage())
            .keyword(consultantSignUpRequest.getKeyword())
            .style(consultantSignUpRequest.getStyle())
            .profile(consultantSignUpRequest.getProfile())
            .certifiedQualification(consultantSignUpRequest.getCertifiedQualification())
            .experience(consultantSignUpRequest.getExperience())
            .availableDays(consultantSignUpRequest.getAvailableDays())
            .role(Role.CONSULTANT)
            .createdAt(LocalDateTime.now())
            .build()
            ;

        authRepository.save(user);

        return "Consultant Registered Successfully";
    }

    public String editUserInformation(UserUpdateRequest userUpdateRequest, Long userId) {
        User user = authRepository.findUserById(userId);

        if (userUpdateRequest.getEmail() != null && user.getId() == userId) {
            authRepository.existsUserByEmail(userUpdateRequest.getEmail());
            emailService.isEmailVerified(userUpdateRequest.getEmail());
        }

        user.update(userUpdateRequest);

        return "User Updated Successfully";
    }

    public FindIdResponse findId(EmailRequest emailRequest) {
        String email = emailRequest.getEmail();

        authRepository.existsUserByEmail(email);
        emailService.isEmailVerified(emailRequest.getEmail());
        User user = authRepository.findUserByEmail(email);

        return FindIdResponse.toDto(user);
    }

    public String resetPassword(UserPasswordResetRequest userPasswordResetRequest) {
        String email = userPasswordResetRequest.getEmail();
        String userId = userPasswordResetRequest.getUserId();
        String newPassword = userPasswordResetRequest.getNewPassword();

        User user = authRepository.findUserByEmailAndUserId(email, userId);

        if(user == null) {
            throw new EntityNotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        emailService.isEmailVerified(email);

        user.update(passwordEncoder.encode(newPassword));

        return "User Password Reset Successfully";
    }

    @Transactional
    public LoginResponse login(LoginReqeust loginReqeust) {
        String userId = loginReqeust.getUserId();
        String password = loginReqeust.getPassword();

        User user = authRepository.findUserByUserIdAndPassword(userId, password);

        if (user.getRole().equals(Role.USER)) {
            return userLogin(loginReqeust);
        } else if (user.getRole().equals(Role.CONSULTANT)) {
            return consultantLogin(loginReqeust);
        } else {
            throw new EntityNotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }
    
    // TODO : 합친 후 수정 필요
    public LoginResponse userLogin(LoginReqeust loginReqeust) {
        return LoginResponse.builder().accessToken("123").refreshToken("123").build();
    }

    public LoginResponse consultantLogin(LoginReqeust loginReqeust) {
        return LoginResponse.builder().accessToken("123").refreshToken("123").build();
    }

    @Transactional
    public void logout(HttpServletRequest request) {}
}
