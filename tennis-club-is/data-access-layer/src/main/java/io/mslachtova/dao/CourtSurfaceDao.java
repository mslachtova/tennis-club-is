package io.mslachtova.dao;

import io.mslachtova.entity.CourtSurface;

import java.util.List;

/**
 * Interface for court surface data access object
 *
 * @author Monika Slachtova
 */
public interface CourtSurfaceDao {
    /**
     * Persists court surface into database
     *
     * @param courtSurface
     */
    void create(CourtSurface courtSurface);

    /**
     * Retrieves a court surface with a given id
     *
     * @param id -- given id
     * @return court surface
     */
    CourtSurface findById(Long id);

    /**
     * Retrieves all court surfaces
     *
     * @return list of court surfaces
     */
    List<CourtSurface> findAll();
}
