package io.mslachtova.service;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dao.CourtDao;
import io.mslachtova.entity.Court;
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
class CourtServiceTest {
    @Mock
    private CourtDao courtDao;

    @Autowired
    @InjectMocks
    private CourtService courtService;

    private Court court;

    @BeforeEach
    void setUp() {
        CourtSurface courtSurface = new CourtSurface("grass", 200.0);
        court = new Court(courtSurface);
    }

    @Test
    void create() {
        courtService.create(court);
        verify(courtDao).create(court);
    }

    @Test
    void findById() {
        when(courtDao.findById(1L)).thenReturn(court);
        assertThat(courtService.findById(1L)).isEqualTo(court);
    }

    @Test
    void findAll() {
        courtService.findAll();
        verify(courtDao).findAll();
    }

    @Test
    void findByCourtNumber() {
        when(courtDao.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        assertThat(courtService.findByCourtNumber(court.getCourtNumber())).isEqualTo(court);
    }

    @Test
    void update() {
        courtService.update(court);
        verify(courtDao).update(court);
    }
}