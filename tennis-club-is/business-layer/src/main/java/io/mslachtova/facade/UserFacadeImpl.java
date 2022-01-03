package io.mslachtova.facade;

import io.mslachtova.dto.UserDto;
import io.mslachtova.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.UserService;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
@Transactional
public class UserFacadeImpl implements UserFacade {
    @Autowired
    private UserService userService;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public void create(UserDto user) {
        if (user.getTelephoneNumber() == null)
            throw new IllegalArgumentException("The telephone number cannot be null.");
        if (user.getName() == null)
            throw new IllegalArgumentException("The name cannot be null.");
        if (userService.findByTelephoneNumber(user.getTelephoneNumber()) != null)
            throw new IllegalArgumentException("The user with telephone number " + user.getTelephoneNumber()
                    + " already exists.");
        userService.create(beanMapper.mapTo(user, User.class));
    }

    @Override
    public UserDto findById(Long id) {
        return beanMapper.mapTo(userService.findById(id), UserDto.class);
    }

    @Override
    public List<UserDto> findAll() {
        return beanMapper.mapTo(userService.findAll(), UserDto.class);
    }

    @Override
    public UserDto findByTelephoneNumber(String telephoneNumber) {
        User user = userService.findByTelephoneNumber(telephoneNumber);
        return  user != null ? beanMapper.mapTo(user, UserDto.class) : null;
    }

    @Override
    public void update(UserDto user) {
        userService.update(beanMapper.mapTo(user, User.class));
    }
}
