package dao;

import entity.Reservation;

import java.util.List;

/**
 * Interface for reservation data access object
 *
 * @author Monika Slachtova
 */
public interface ReservationDao {
    /**
     * Persists reservation into database
     *
     * @param reservation
     */
    void create(Reservation reservation);

    /**
     * Retrieves a reservation with a given id
     *
     * @param id -- given id
     * @return reservation
     */
    Reservation findById(Long id);

    /**
     * Retrieves all reservations
     *
     * @return list of reservations
     */
    List<Reservation> findAll();
}
