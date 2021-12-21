package io.mslachtova.dao;

import io.mslachtova.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Monika Slachtova
 */
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User user) {
        em.persist(user);
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User findByTelephoneNumber(String telephoneNumber) {
        return em.createQuery("select u from User u where u.telephoneNumber = :telephoneNumber", User.class)
                .setParameter("telephoneNumber", telephoneNumber)
                .getSingleResult();
    }
}
