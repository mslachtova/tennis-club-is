package service;

import io.mslachtova.entity.Reservation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for reservation service
 *
 * @author Monika Slachtova
 */
@Service
public interface ReservationService {
    /**
     * Create a new reservation
     *
     * @param reservation
     */
    void create(Reservation reservation);

    /**
     * Find a reservation by id
     *
     * @param id -- given id
     * @return reservation
     */
    Reservation findById(Long id);

    /**
     * Find all reservations
     *
     * @return list of reservations
     */
    List<Reservation> findAll();

    /**
     * Count total price for a given Reservation
     *
     * @param reservation
     * @return total price
     */
    double getTotalPrice(Reservation reservation);
}
