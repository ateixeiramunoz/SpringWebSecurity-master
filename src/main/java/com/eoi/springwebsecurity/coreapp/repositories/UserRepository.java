package com.eoi.springwebsecurity.coreapp.repositories;
import com.eoi.springwebsecurity.coreapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * The interface User repository.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by email user.
     *
     * @param email the email
     *
     * @return the user
     */
    User findByEmail(String email);

    /**
     * Find by reset password token user.
     *
     * @param token the token
     *
     * @return the user
     */
    User findByResetPasswordToken(String token);
}