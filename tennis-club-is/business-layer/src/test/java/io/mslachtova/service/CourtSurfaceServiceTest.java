package io.mslachtova.service;


import io.mslachtova.ServiceConfig;
import io.mslachtova.dao.CourtSurfaceDao;
import io.mslachtova.entity.CourtSurface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class CourtSurfaceServiceTest {
    @Mock
    private CourtSurfaceDao courtSurfaceDao;

    @Autowired
    @InjectMocks
    private CourtSurfaceService courtSurfaceService;

    private CourtSurface courtSurface;

    @BeforeEach
    void setUp() {
        courtSurface = new CourtSurface("grass", 200.0);
    }

    @Test
    void create() {
        courtSurfaceService.create(courtSurface);
        verify(courtSurfaceDao).create(courtSurface);
    }

    @Test
    void findById() {
        when(courtSurfaceDao.findById(1L)).thenReturn(courtSurface);
        assertThat(courtSurfaceDao.findById(1L)).isEqualTo(courtSurface);
    }

    @Test
    void findAll() {
        courtSurfaceService.findAll();
        verify(courtSurfaceDao).findAll();
    }

    @Test
    void update() {
        courtSurfaceService.update(courtSurface);
        verify(courtSurfaceDao).update(courtSurface);
    }
}