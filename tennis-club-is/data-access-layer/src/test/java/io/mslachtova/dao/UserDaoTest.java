package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static io.mslachtova.dao.TestHelper.getAlice;
import static io.mslachtova.dao.TestHelper.getBob;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertThrows;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;

    @BeforeClass
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            user1 = getAlice();
            em.persist(user1);

            user2 = getBob();
            em.persist(user2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test(dependsOnMethods = "findById")
    void create() {
        User user = new User("768995125", "Jane Doe");
        userDao.create(user);
        assertThat(userDao.findById(user.getId())).isEqualTo(user);
    }

    @Test
    void createAlreadyExistingTelephoneNumber() {
        assertThrows(DataAccessException.class, () -> {
           User user = new User("745558238", "Jane Doe");
           userDao.create(user);
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