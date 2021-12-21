package facade;

import dto.CourtSurfaceDto;
import io.mslachtova.entity.CourtSurface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BeanMapper;
import service.CourtSurfaceService;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
@Transactional
public class CourtSurfaceFacadeImpl implements CourtSurfaceFacade {
    @Autowired
    private CourtSurfaceService courtSurfaceService;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public void create(CourtSurfaceDto courtSurface) {
        courtSurfaceService.create(beanMapper.mapTo(courtSurface, CourtSurface.class));
    }

    @Override
    public CourtSurfaceDto findById(Long id) {
        return beanMapper.mapTo(courtSurfaceService.findById(id), CourtSurfaceDto.class);
    }

    @Override
    public List<CourtSurfaceDto> findAll() {
        return beanMapper.mapTo(courtSurfaceService.findAll(), CourtSurfaceDto.class);
    }
}
