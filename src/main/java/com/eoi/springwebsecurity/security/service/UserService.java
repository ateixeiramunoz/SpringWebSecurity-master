package com.eoi.springwebsecurity.security.service;


import com.eoi.springwebsecurity.coreapp.dto.UserDto;
import com.eoi.springwebsecurity.coreapp.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * The interface User service.
 */
@Service
public interface UserService {
    /**
     * Save user.
     *
     * @param userDto the user dto
     */
    void saveUser(UserDto userDto);

    /**
     * Find user by email user.
     *
     * @param email the email
     *
     * @return the user
     */
    User findUserByEmail(String email);

    /**
     * Find all users list.
     *
     * @return the list
     */
    List<UserDto> findAllUsers();

    /**
     * Gets by reset password token.
     *
     * @param token the token
     *
     * @return the by reset password token
     */
    User getByResetPasswordToken(String token);

    /**
     * Update reset password token.
     *
     * @param token the token
     * @param email the email
     */
    void updateResetPasswordToken(String token, String email);

    /**
     * Update password.
     *
     * @param user     the user
     * @param password the password
     */
    void updatePassword(User user, String password);
}