package io.mslachtova.facade;

import io.mslachtova.dto.CourtDto;
import io.mslachtova.entity.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.CourtService;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
@Transactional
public class CourtFacadeImpl implements CourtFacade {
    @Autowired
    private CourtService courtService;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public void create(CourtDto court) {
        if (court.getCourtSurface() == null)
            throw new IllegalArgumentException("The court surface cannot be null.");
        courtService.create(beanMapper.mapTo(court, Court.class));
    }

    @Override
    public CourtDto findById(Long id) {
        return beanMapper.mapTo(courtService.findById(id), CourtDto.class);
    }

    @Override
    public List<CourtDto> findAll() {
        return beanMapper.mapTo(courtService.findAll(), CourtDto.class);
    }

    @Override
    public CourtDto findByCourtNumber(int courtNumber) {
        return beanMapper.mapTo(courtService.findByCourtNumber(courtNumber), CourtDto.class);
    }

    @Override
    public void update(CourtDto court) {
        courtService.update(beanMapper.mapTo(court, Court.class));
    }
}
