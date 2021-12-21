package io.mslachtova.dao;

import io.mslachtova.entity.Reservation;

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

    /**
     * Updates reservation in database
     *
     * @param reservation -- reservation to be updated
     */
    void update(Reservation reservation);
}
