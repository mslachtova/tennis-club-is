package io.mslachtova.dao;

import io.mslachtova.entity.Court;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Monika Slachtova
 */
@Repository
public class CourtDaoImpl implements CourtDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Court court) {
        em.persist(court);
    }

    @Override
    public Court findById(Long id) {
        return em.find(Court.class, id);
    }

    @Override
    public List<Court> findAll() {
        return em.createQuery("select c from Court c", Court.class).getResultList();
    }

    @Override
    public Court findByCourtNumber(int courtNumber) {
        return em.createQuery("select c from Court c where c.courtNumber = :courtNumber", Court.class)
                .setParameter("courtNumber", courtNumber)
                .getSingleResult();
    }
}
