package dao;

import entity.Court;

import java.util.List;

/**
 * Interface for court data access object
 *
 * @author Monika Slachtova
 */
public interface CourtDao {
    /**
     * Persists court into database
     *
     * @param court
     */
    void create(Court court);

    /**
     * Retrieves a court with a given id
     *
     * @param id -- given id
     * @return court
     */
    Court findById(Long id);

    /**
     * Retrieves all courts
     *
     * @return list of courts
     */
    List<Court> findAll();

    /**
     * Retrieves a court with a given court number
     * @param courtNumber -- given court number
     * @return court
     */
    Court findByCourtNumber(int courtNumber);
}
