package io.mslachtova.facade;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dto.CourtDto;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.CourtService;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = ServiceConfig.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Transactional
class CourtFacadeTest {
    @Mock
    private CourtService courtService;

    @Spy
    @Autowired
    private BeanMapper beanMapper;

    @InjectMocks
    private CourtFacade courtFacade = new CourtFacadeImpl();

    private Court court;
    private CourtDto courtDto;

    @BeforeEach
    void setUp() {
        court = new Court(new CourtSurface("grass", 200.0));
        courtDto = beanMapper.mapTo(court, CourtDto.class);
    }

    @Test
    void create() {
        courtFacade.create(courtDto);
        verify(courtService).create(court);
    }

    @Test
    void createNullCourtSurface() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> courtFacade
                .create(new CourtDto(null)));
        assertThat(exception.getMessage()).isEqualTo("The court surface cannot be null.");
    }

    @Test
    void findById() {
        when(courtService.findById(2L)).thenReturn(court);
        assertThat(courtFacade.findById(2L)).isEqualTo(courtDto);
    }

    @Test
    void findAll() {
        courtFacade.findAll();
        verify(courtService).findAll();
    }

    @Test
    void findByCourtNumber() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        assertThat(courtFacade.findByCourtNumber(court.getCourtNumber())).isEqualTo(courtDto);
    }

    @Test
    void update() {
        courtFacade.update(courtDto);
        verify(courtService).update(court);
    }
}