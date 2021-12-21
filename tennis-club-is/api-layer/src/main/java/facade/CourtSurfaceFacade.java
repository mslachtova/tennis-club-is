package facade;

import dto.CourtSurfaceDto;

import java.util.List;

/**
 * Interface for court surface facade
 *
 * @author Monika Slachtova
 */
public interface CourtSurfaceFacade {
    /**
     * Create a new court surface
     *
     * @param courtSurface
     */
    void create(CourtSurfaceDto courtSurface);

    /**
     * Find a court surface with by id
     *
     * @param id -- given id
     * @return court surface
     */
    CourtSurfaceDto findById(Long id);

    /**
     * Find all court surfaces
     *
     * @return list of court surfaces
     */
    List<CourtSurfaceDto> findAll();
}
