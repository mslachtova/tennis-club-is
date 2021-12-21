package service;

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
    @Autowired
    private CourtSurfaceDao courtSurfaceDao;

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
