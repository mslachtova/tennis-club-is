package io.mslachtova.service;

import io.mslachtova.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for user service
 *
 * @author Monika Slachtova
 */
@Service
public interface UserService {
    /**
     * Create a new user
     *
     * @param user -- user to be created
     */
    void create(User user);

    /**
     * Find a user by id
     *
     * @param id -- given id
     * @return user
     */
    User findById(Long id);

    /**
     * Find all users
     *
     * @return list of users
     */
    List<User> findAll();

    /**
     * Find a user by telephone number
     *
     * @param telephoneNumber -- given telephone number
     * @return user
     */
    User findByTelephoneNumber(String telephoneNumber);

    /**
     * Update a user
     *
     * @param user -- user to be updated
     */
    void update(User user);
}
