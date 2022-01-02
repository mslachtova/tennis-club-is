package io.mslachtova.facade;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dto.CourtSurfaceDto;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.CourtSurfaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = ServiceConfig.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Transactional
class CourtSurfaceFacadeTest {
    @Mock
    private CourtSurfaceService courtSurfaceService;

    @Spy
    @Autowired
    private BeanMapper beanMapper;

    @InjectMocks
    private CourtSurfaceFacade courtSurfaceFacade = new CourtSurfaceFacadeImpl();

    private CourtSurface courtSurface;
    private CourtSurfaceDto courtSurfaceDto;

    @BeforeEach
    void setUp() {
        courtSurface = new CourtSurface("grass", 200.0);
        courtSurfaceDto = beanMapper.mapTo(courtSurface, CourtSurfaceDto.class);
    }

    @Test
    void create() {
        courtSurfaceFacade.create(courtSurfaceDto);
        verify(courtSurfaceService).create(courtSurface);
    }

    @Test
    void findById() {
        when(courtSurfaceService.findById(1L)).thenReturn(courtSurface);
        assertThat(courtSurfaceFacade.findById(1L)).isEqualTo(courtSurfaceDto);
    }

    @Test
    void findAll() {
        courtSurfaceFacade.findAll();
        verify(courtSurfaceService).findAll();
    }

    @Test
    void update() {
        courtSurfaceFacade.update(courtSurfaceDto);
        verify(courtSurfaceService).update(courtSurface);
    }
}