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
    private final UserService userService;

    private final BeanMapper beanMapper;

    @Autowired
    public UserFacadeImpl(UserService userService, BeanMapper beanMapper) {
        this.userService = userService;
        this.beanMapper = beanMapper;
    }

    @Override
    public void create(UserDto user) {
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
        return null;
    }

    @Override
    public void update(UserDto user) {
        userService.update(beanMapper.mapTo(user, User.class));
    }
}
