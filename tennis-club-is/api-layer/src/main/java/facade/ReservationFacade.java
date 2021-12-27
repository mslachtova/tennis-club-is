package facade;

import dto.ReservationCreateDto;
import dto.ReservationDto;
import dto.ReservationTotalPriceDto;

import java.util.List;

/**
 * Interface for reservation facade
 *
 * @author Monika Slachtova
 */
public interface ReservationFacade {
    /**
     * Create a new reservation
     *
     * @param reservation
     */
    void create(ReservationCreateDto reservation);

    /**
     * Find a reservation by id
     *
     * @param id -- given id
     * @return reservation
     */
    ReservationDto findById(Long id);

    /**
     * Find all reservations
     *
     * @return list of reservations
     */
    List<ReservationDto> findAll();

    /**
     * Count total price for a reservation by id
     *
     * @param id -- given id
     * @return object with information about the reservation ant its total price
     */
    ReservationTotalPriceDto getTotalPrice(long id);

    /**
     * Update a reservation
     *
     * @param reservation -- reservation to be updated
     */
    void update(ReservationDto reservation);

    /**
     * Get all reservations for a court with given court number
     *
     * @param courtNumber -- given court number
     * @return list of reservations
     */
    List<ReservationDto> getReservationsByCourtNumber(int courtNumber);

    /**
     * Get all reservations for an user with given telephone number
     *
     * @param telephoneNumber -- given telephone number
     * @return list of reservations
     */
    List<ReservationDto> getReservationsByTelephoneNumber(String telephoneNumber);
}
