package io.mslachtova.facade;

import io.mslachtova.ServiceConfig;
import io.mslachtova.dto.ReservationCreateDto;
import io.mslachtova.dto.ReservationDto;
import io.mslachtova.dto.ReservationTotalPriceDto;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import io.mslachtova.enums.GameType;
import io.mslachtova.service.BeanMapper;
import io.mslachtova.service.CourtService;
import io.mslachtova.service.ReservationService;
import io.mslachtova.service.UserService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = ServiceConfig.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@Transactional
class ReservationFacadeTest {
    @Mock
    private CourtService courtService;

    @Mock
    private UserService userService;

    @Mock
    private ReservationService reservationService;

    @Spy
    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    @InjectMocks
    private ReservationFacade reservationFacade;

    private Court court;
    private User user1;
    private User user2;
    private Reservation reservation1;
    private ReservationDto reservationDto1;
    private Reservation reservation2;
    private ReservationDto reservationDto2;

    @BeforeEach
    void setUp() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        when(userService.findByTelephoneNumber(user1.getTelephoneNumber())).thenReturn(user1);
        when(userService.findByTelephoneNumber(user2.getTelephoneNumber())).thenReturn(user2);

        CourtSurface courtSurface = new CourtSurface("clay", 160.0);
        court = new Court(courtSurface);
        user1 = new User("777777777", "Alex Taylor");
        user2 = new User("999999999", "Bob Taylor");
        reservation1 = new Reservation(LocalDateTime.of(2022, 3, 2, 14, 15),
                LocalDateTime.of(2022, 3, 2, 14, 45), GameType.DOUBLES);
        reservationDto1 = beanMapper.mapTo(reservation1, ReservationDto.class);
        reservation2 = new Reservation(LocalDateTime.of(2022, 3, 2, 15, 15),
                LocalDateTime.of(2022, 3, 2, 16, 45), GameType.SINGLES);
        reservationDto2 = beanMapper.mapTo(reservation2, ReservationDto.class);
        court.setReservations(List.of(reservation1, reservation2));
        court.addReservation(reservation1);
        court.addReservation(reservation2);
        user1.addReservation(reservation1);
        user2.addReservation(reservation2);
    }

    @Test
    void createExistingTelephoneNumberSameName() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        assertCreateReservation(reservationCreateDto);
        verify(userService, never()).create(any(User.class));
    }

    @Test
    void createNewTelephoneNumber() {
        User user = new User("888888888", "Josh Smith");
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setTelephoneNumber(user.getTelephoneNumber());
        reservationCreateDto.setName(user.getName());
        assertCreateReservation(reservationCreateDto);
        verify(userService).create(user);
    }

    @Test
    void createExistingTelephoneNumberDifferentName() {
        User user = new User("777777777", "Josh Smith");
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setTelephoneNumber(user.getTelephoneNumber());
        reservationCreateDto.setName(user.getName());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("Telephone number " + reservationCreateDto.getTelephoneNumber()
                + "has already been attached to name " + user1.getName() + ".");
    }

    @Test
    void createNonExistingCourtNumber() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setCourtNumber(10);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("Court with court number " + reservationCreateDto.getCourtNumber()
                + " not found.");
    }

    @Test
    void createFromEqualsTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 13, 15));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 13, 15));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo(reservationCreateDto.getTo() + " is not after "
                + reservationCreateDto.getFrom());
    }

    @Test
    void createFromAfterTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 15));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 13, 15));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo(reservationCreateDto.getTo() + " is not after "
                + reservationCreateDto.getFrom());
    }

    @Test
    void createNoCollisionEndBeforeFrom() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 13, 15));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 15));
        assertCreateReservation(reservationCreateDto);
    }

    @Test
    void createNoCollisionStartAfterTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 45));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 15));
        assertCreateReservation(reservationCreateDto);
    }

    @Test
    void createCollisionStartBeforeFromEndAfterTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 13, 0));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 0));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartBeforeFromEndBeforeTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 20));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 30));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartAfterFromEndBeforeTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 20));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 30));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartBeforeToEndAfterTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 30));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 0));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void findById() {
        when(reservationService.findById(4L)).thenReturn(reservation1);
        assertThat(reservationFacade.findById(4L)).isEqualTo(reservationDto1);
    }

    @Test
    void findAll() {
        reservationFacade.findAll();
        verify(reservationService).findAll();
    }

    @Test
    void getTotalPrice() {
        when(reservationService.findById(2L)).thenReturn(reservation2);
        when(reservationService.getTotalPrice(reservation2)).thenReturn(160.0);
        ReservationTotalPriceDto reservationTotalPriceDto = reservationFacade.getTotalPrice(2L);
        assertThat(reservationTotalPriceDto.getReservation()).isEqualTo(reservationDto2);
        assertThat(reservationTotalPriceDto.getTotalPrice()).isEqualTo(160.0);
    }

    @Test
    void update() {
        reservationFacade.update(reservationDto1);
        verify(reservationService).update(reservation1);
    }

    @Test
    void getReservationsByCourtNumber() {
        List<ReservationDto> foundReservations = reservationFacade.getReservationsByCourtNumber(court.getCourtNumber());
        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservationDto1, reservationDto2);
    }

    @Test
    void getReservationsByTelephoneNumber() {
        List<ReservationDto> foundReservations = reservationFacade
                .getReservationsByTelephoneNumber(user2.getTelephoneNumber());
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservationDto2);
    }

    private ReservationCreateDto getReservationCreateDto() {
        ReservationCreateDto reservationCreateDto = new ReservationCreateDto();
        reservationCreateDto.setCourtNumber(reservation1.getCourt().getCourtNumber());
        reservationCreateDto.setFrom(reservation1.getFrom());
        reservationCreateDto.setTo(reservation1.getTo());
        reservationCreateDto.setGameType(reservation1.getGameType());
        reservationCreateDto.setTelephoneNumber(reservation1.getUser().getTelephoneNumber());
        reservationCreateDto.setName(reservation1.getUser().getName());
        return reservationCreateDto;
    }

    private void assertCreateReservation(ReservationCreateDto reservationCreateDto) {
        Reservation reservation = beanMapper.mapTo(reservationCreateDto, Reservation.class);
        reservation.setId(3L);
        when(reservationService.create(reservation)).thenReturn(reservation);

        Long newId = reservationFacade.create(reservationCreateDto);

        verify(courtService).update(court);
        verify(userService).update(user1);
        assertThat(court.getReservations()).contains(reservation1, reservation2, reservation);
        assertThat(user1.getReservations()).contains(reservation1, reservation);
        assertThat(newId).isEqualTo(3L);
    }
}