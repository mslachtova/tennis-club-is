package facade;

import dto.UserDto;

import java.util.List;

/**
 * Interface for user facade
 *
 * @author Monika Slachtova
 */
public interface UserFacade {
    /**
     * Create a new user
     *
     * @param user
     */
    void create(UserDto user);

    /**
     * Find a user by id
     *
     * @param id -- given id
     * @return user
     */
    UserDto findById(Long id);

    /**
     * Find all users
     *
     * @return list of users
     */
    List<UserDto> findAll();

    /**
     * Find a user by telephone number
     *
     * @param telephoneNumber -- given telephone number
     * @return user
     */
    UserDto findByTelephoneNumber(int telephoneNumber);
}
