package facade;

import dto.CourtDto;

import java.util.List;

/**
 * Interface for court facade
 *
 * @author Monika Slachtova
 */
public interface CourtFacade {
    /**
     * Create a new court
     *
     * @param court
     */
    void create(CourtDto court);

    /**
     * Find a court by id
     *
     * @param id -- given id
     * @return court
     */
    CourtDto findById(Long id);

    /**
     * Find all courts
     *
     * @return list of courts
     */
    List<CourtDto> findAll();

    /**
     * Find a court by court number
     *
     * @param courtNumber -- given court number
     * @return court
     */
    CourtDto findByCourtNumber(int courtNumber);

    /**
     * Update a court
     *
     * @param court -- court to be updated
     */
    void update(CourtDto court);
}
