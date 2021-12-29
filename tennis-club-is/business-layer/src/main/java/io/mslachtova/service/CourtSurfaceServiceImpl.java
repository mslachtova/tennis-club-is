package io.mslachtova.service;

import io.mslachtova.dao.CourtSurfaceDao;
import io.mslachtova.entity.CourtSurface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
public class CourtSurfaceServiceImpl implements CourtSurfaceService {
    private final CourtSurfaceDao courtSurfaceDao;

    @Autowired
    public CourtSurfaceServiceImpl(CourtSurfaceDao courtSurfaceDao) {
        this.courtSurfaceDao = courtSurfaceDao;
    }

    @Override
    public void create(CourtSurface courtSurface) {
        courtSurfaceDao.create(courtSurface);
    }

    @Override
    public CourtSurface findById(Long id) {
        return courtSurfaceDao.findById(id);
    }

    @Override
    public List<CourtSurface> findAll() {
        return courtSurfaceDao.findAll();
    }

    @Override
    public void update(CourtSurface courtSurface) {
        courtSurfaceDao.update(courtSurface);
    }
}
