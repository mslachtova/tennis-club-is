package service;

import io.mslachtova.entity.Court;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for court service
 *
 * @author Monika Slachtova
 */
@Service
public interface CourtService {
    /**
     * Create a new court
     *
     * @param court
     */
    void create(Court court);

    /**
     * Find a court by id
     *
     * @param id -- given id
     * @return court
     */
    Court findById(Long id);

    /**
     * Find all courts
     *
     * @return list of courts
     */
    List<Court> findAll();

    /**
     * Find a court by court number
     *
     * @param courtNumber -- given court number
     * @return court
     */
    Court findByCourtNumber(int courtNumber);
}
