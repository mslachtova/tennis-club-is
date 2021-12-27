package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
class UserDaoTest {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            user1 = new User();
            user1.setTelephoneNumber("745558238");
            user1.setName("Alice Smith");
            em.persist(user1);

            user2 = new User();
            user2.setTelephoneNumber("766456991");
            user2.setName("Bob Taylor");
            em.persist(user2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        User user = new User();
        user.setTelephoneNumber("768995125");
        user.setName("Jane Doe");
        userDao.create(user);
        assertThat(userDao.findById(user.getId())).isEqualTo(user);
    }

    @Test
    void createNullTelephoneNumber() {
        assertThrows(DataAccessException.class, () -> {
            User user = new User();
            user.setName("John Doe");
            userDao.create(user);
        });
    }

    @Test
    void createNullName() {
        assertThrows(DataAccessException.class, () -> {
            User user = new User();
            user.setTelephoneNumber("758888994");
            userDao.create(user);
        });
    }

    @Test
    void createAlreadyExistingTelephoneNumber() {
        assertThrows(DataAccessException.class, () -> {
           User user = new User();
           user.setTelephoneNumber("745558238");
           user.setName("Jane Doe");
        });
    }

    @Test
    void findById() {
        assertThat(userDao.findById(user2.getId())).isEqualTo(user2);
    }

    @Test
    void findAll() {
        List<User> foundUsers = userDao.findAll();
        assertThat(foundUsers.size()).isEqualTo(2);
        assertThat(foundUsers).contains(user1, user2);
    }

    @Test
    void findByTelephoneNumber() {
        assertThat(userDao.findByTelephoneNumber(user1.getTelephoneNumber())).isEqualTo(user1);
    }

    @Test
    void findByNonExistingTelephoneNumber() {
        assertThat(userDao.findByTelephoneNumber("999999999")).isNull();
    }

    @Test
    void update() {
        String newName = "Josh Taylor";
        user2.setName(newName);
        userDao.update(user2);
        assertThat(userDao.findById(user2.getId()).getName()).isEqualTo(newName);
    }
}