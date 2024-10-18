package com.signal.domain.auth.repository;

import com.signal.domain.auth.model.User;
import com.signal.global.exception.errorCode.ErrorCode;
import com.signal.global.exception.handler.EntityNotFoundException;
import com.signal.global.exception.handler.InvalidValueException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthRepository extends JpaRepository<User, Long> {

    default User findUserById(Long id) {
        return findById(id)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findUserByEmail(String email);

    default boolean existsUserByEmail(String email) {
        if (findUserByEmail(email) == null) throw new InvalidValueException(ErrorCode.DUPLICATE_EMAIL);
        return true;
    }

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.userId = :userId")
    User findUserByEmailAndUserId(@Param("email") String email, @Param("userId") String userId);

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.password = :password")
    User findUserByUserIdAndPassword(@Param("userId") String userId, @Param("password") String password);

    User findByUserId(String userId);
}
