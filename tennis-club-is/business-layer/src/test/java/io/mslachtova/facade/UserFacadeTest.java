package io.mslachtova.facade;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dto.UserDto;
import io.mslachtova.entity.User;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
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
class UserFacadeTest {
    @Mock
    private UserService userService;

    @Spy
    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    @InjectMocks
    private UserFacade userFacade;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        user = new User("756988452","John Doe");
        userDto = beanMapper.mapTo(user, UserDto.class);
    }

    @Test
    void create() {
        userFacade.create(userDto);
        verify(userService).create(user);
    }

    @Test
    void findById() {
        when(userService.findById(3L)).thenReturn(user);
        assertThat(userFacade.findById(3L)).isEqualTo(userDto);
    }

    @Test
    void findAll() {
        userFacade.findAll();
        verify(userService).findAll();
    }

    @Test
    void findByTelephoneNumber() {
        when(userService.findByTelephoneNumber(user.getTelephoneNumber())).thenReturn(user);
        assertThat(userFacade.findByTelephoneNumber(user.getTelephoneNumber())).isEqualTo(userDto);
    }
}