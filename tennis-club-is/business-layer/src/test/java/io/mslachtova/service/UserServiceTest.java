package io.mslachtova.service;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dao.UserDao;
import io.mslachtova.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = ServiceConfig.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Transactional
class UserServiceTest {
    @Mock
    private UserDao userDao;

    @Autowired
    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("745888213", "Jane Doe");
    }

    @Test
    void create() {
        userService.create(user);
        verify(userDao).create(user);
    }

    @Test
    void findById() {
        when(userDao.findById(2L)).thenReturn(user);
        assertThat(userService.findById(2L)).isEqualTo(user);
    }

    @Test
    void findAll() {
        userService.findAll();
        verify(userDao).findAll();
    }

    @Test
    void findByTelephoneNumber() {
        when(userDao.findByTelephoneNumber(user.getTelephoneNumber())).thenReturn(user);
        assertThat(userService.findByTelephoneNumber(user.getTelephoneNumber())).isEqualTo(user);
    }

    @Test
    void update() {
        userService.update(user);
        verify(userDao).update(user);
    }
}