package com.signal.domain.auth.model;

import com.signal.domain.auth.dto.request.UserPasswordResetRequest;
import com.signal.domain.auth.dto.request.UserSignUpRequest;
import com.signal.domain.auth.dto.request.UserUpdateRequest;
import com.signal.domain.auth.model.enums.AvailableDays;
import com.signal.domain.auth.model.enums.Gender;
import com.signal.domain.auth.model.enums.Role;
import com.signal.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    private String email;

    private String image;

    private String keyword;

    private String style;

    private Double totalRating;

    @Enumerated(EnumType.STRING)
    private AvailableDays availableDays;

    private String profile;

    private String certifiedQualification;

    private String experience;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @LastModifiedDate
    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;


    public void update(UserUpdateRequest userUpdateRequest) {
        if(userUpdateRequest.getEmail() != null) {
            this.email = userUpdateRequest.getEmail();
        }

        if(userUpdateRequest.getNickname() != null) {
            this.nickname = userUpdateRequest.getNickname();
        }

        if(userUpdateRequest.getImage() != null) {
            this.image = userUpdateRequest.getImage();
        }
    }

    public void update(String newPassword) {
        this.password = newPassword;
    }
}
