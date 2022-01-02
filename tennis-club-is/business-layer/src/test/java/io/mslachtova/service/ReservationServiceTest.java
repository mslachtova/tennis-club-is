package io.mslachtova.service;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dao.ReservationDao;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import io.mslachtova.enums.GameType;
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

import java.time.LocalDateTime;

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
class ReservationServiceTest {
    @Mock
    private ReservationDao reservationDao;

    @Autowired
    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        CourtSurface courtSurface = new CourtSurface("grass", 200.0);
        Court court = new Court(courtSurface);
        User user = new User("759888221", "Alice Smith");
        reservation = new Reservation(LocalDateTime.of(2022, 1, 3, 14, 0),
                LocalDateTime.of(2022, 1, 3, 15, 30),
                GameType.SINGLES);
        court.addReservation(reservation);
        user.addReservation(reservation);
    }

    @Test
    void create() {
        reservationService.create(reservation);
        verify(reservationDao).create(reservation);
    }

    @Test
    void findById() {
        when(reservationDao.findById(3L)).thenReturn(reservation);
        assertThat(reservationService.findById(3L)).isEqualTo(reservation);
    }

    @Test
    void findAll() {
        reservationService.findAll();
        verify(reservationDao).findAll();
    }

    @Test
    void getTotalPriceSingles() {
        assertThat(reservationService.getTotalPrice(reservation)).isEqualTo(200.0);
    }

    @Test
    void getTotalPriceDoubles() {
        reservation.setGameType(GameType.DOUBLES);
        assertThat(reservationService.getTotalPrice(reservation)).isEqualTo(300.0);
    }

    @Test
    void update() {
        reservationService.update(reservation);
        verify(reservationDao).update(reservation);
    }
}