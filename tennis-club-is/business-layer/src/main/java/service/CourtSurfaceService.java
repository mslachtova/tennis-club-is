package service;

import io.mslachtova.entity.CourtSurface;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Interface for court surface service
 *
 * @author Monika Slachtova
 */
@Service
public interface CourtSurfaceService {
    /**
     * Create a new court surface
     *
     * @param courtSurface
     */
    void create(CourtSurface courtSurface);

    /**
     * Find a court surface with by id
     *
     * @param id -- given id
     * @return court surface
     */
    CourtSurface findById(Long id);

    /**
     * Find all court surfaces
     *
     * @return list of court surfaces
     */
    List<CourtSurface> findAll();
}