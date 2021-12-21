package service;

import io.mslachtova.dao.UserDao;
import io.mslachtova.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByTelephoneNumber(String telephoneNumber) {
        return userDao.findByTelephoneNumber(telephoneNumber);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
