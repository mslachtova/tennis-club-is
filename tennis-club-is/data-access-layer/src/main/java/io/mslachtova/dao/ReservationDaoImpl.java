package io.mslachtova.dao;

import io.mslachtova.entity.Reservation;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Monika Slachtova
 */
@Repository
public class ReservationDaoImpl implements ReservationDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Reservation reservation) {
        em.persist(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class).getResultList();
    }
}
