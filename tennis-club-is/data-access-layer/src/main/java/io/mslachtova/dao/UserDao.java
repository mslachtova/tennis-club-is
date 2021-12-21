package io.mslachtova.dao;

import io.mslachtova.entity.User;

import java.util.List;

/**
 * Interface for user data access object
 *
 * @author Monika Slachtova
 */
public interface UserDao {
    /**
     * Persists user into database
     *
     * @param user
     */
    void create(User user);

    /**
     * Retrieves a user with a given id
     *
     * @param id -- given id
     * @return user
     */
    User findById(Long id);

    /**
     * Retrieves all users
     *
     * @return list of users
     */
    List<User> findAll();

    /**
     * Retrieves a user with a given telephone number
     *
     * @param telephoneNumber -- given telephone number
     * @return user
     */
    User findByTelephoneNumber(String telephoneNumber);

    /**
     * Updates user in database
     *
     * @param user -- user to be updated
     */
    void update(User user);
}
