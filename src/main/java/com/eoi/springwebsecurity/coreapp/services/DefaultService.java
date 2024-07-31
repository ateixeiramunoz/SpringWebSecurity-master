package com.eoi.springwebsecurity.coreapp.services;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.coreapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;


/**
 * The type Default service.
 */
@Service
public class DefaultService {

    /**
     * The User repository.
     */
    @Autowired
    UserRepository userRepository;


    /**
     * Gets user id from calendario id.
     *
     * @param i the
     *
     * @return the user id from calendario id
     */
    public String getUserIdFromCalendarioID(Integer i)
    {

        Optional<User> user = userRepository.findById(Long.valueOf(i));

        return user.get().getEmail();
    }

}
