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

    @InjectMocks
    private ReservationFacade reservationFacade = new ReservationFacadeImpl();

    private Court court;
    private User user1;
    private User user2;
    private User newUser;
    private Reservation reservation1;
    private ReservationDto reservationDto1;
    private Reservation reservation2;
    private ReservationDto reservationDto2;

    @BeforeEach
    void setUp() {
        CourtSurface courtSurface = new CourtSurface("clay", 160.0);
        court = new Court(courtSurface);
        user1 = new User("777777777", "Alex Taylor");
        user2 = new User("999999999", "Bob Taylor");
        reservation1 = new Reservation(LocalDateTime.of(2022, 3, 2, 14, 15),
                LocalDateTime.of(2022, 3, 2, 14, 45), GameType.DOUBLES);
        reservation2 = new Reservation(LocalDateTime.of(2022, 3, 2, 15, 15),
                LocalDateTime.of(2022, 3, 2, 16, 45), GameType.SINGLES);
        court.addReservation(reservation1);
        court.addReservation(reservation2);
        user1.addReservation(reservation1);
        user2.addReservation(reservation2);
        reservationDto1 = beanMapper.mapTo(reservation1, ReservationDto.class);
        reservationDto2 = beanMapper.mapTo(reservation2, ReservationDto.class);
    }

    @Test
    void createExistingTelephoneNumberSameName() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        assertCreateReservation(reservationCreateDto,true);
        verify(userService, never()).create(any(User.class));
    }

    @Test
    void createNewTelephoneNumber() {
        newUser = new User("888888888", "Josh Smith");
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setTelephoneNumber(newUser.getTelephoneNumber());
        reservationCreateDto.setName(newUser.getName());
        assertCreateReservation(reservationCreateDto,false);
        verify(userService).create(newUser);
    }

    @Test
    void createExistingTelephoneNumberDifferentName() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        when(userService.findByTelephoneNumber(user1.getTelephoneNumber())).thenReturn(user1);
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
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
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
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
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
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 13, 15));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 15));
        assertCreateReservation(reservationCreateDto,true);
    }

    @Test
    void createNoCollisionStartAfterTo() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 45));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 15));
        assertCreateReservation(reservationCreateDto,true);
    }

    @Test
    void createCollisionStartBeforeFromEndAfterTo() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 13, 0));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 0));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartBeforeFromEndBeforeTo() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 20));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 30));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartAfterFromEndBeforeTo() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 20));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 14, 30));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createCollisionStartBeforeToEndAfterTo() {
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(LocalDateTime.of(2022, 3, 2, 14, 30));
        reservationCreateDto.setTo(LocalDateTime.of(2022, 3, 2, 15, 0));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The time interval has already been reserved.");
    }

    @Test
    void createNullFrom() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setFrom(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The from date cannot be null.");
    }

    @Test
    void createNullTo() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setTo(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The to date cannot be null.");
    }

    @Test
    void createNullGameType() {
        ReservationCreateDto reservationCreateDto = getReservationCreateDto();
        reservationCreateDto.setGameType(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reservationFacade
                .create(reservationCreateDto));
        assertThat(exception.getMessage()).isEqualTo("The game type cannot be null.");
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
        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        List<ReservationDto> foundReservations = reservationFacade.getReservationsByCourtNumber(court.getCourtNumber());
        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservationDto1, reservationDto2);
    }

    @Test
    void getReservationsByTelephoneNumber() {
        when(userService.findByTelephoneNumber(user2.getTelephoneNumber())).thenReturn(user2);
        List<ReservationDto> foundReservations = reservationFacade
                .getReservationsByTelephoneNumber(user2.getTelephoneNumber());
        assertThat(foundReservations.size()).isEqualTo(1);
        assertThat(foundReservations).contains(reservationDto2);
    }

    private ReservationCreateDto getReservationCreateDto() {
        return new ReservationCreateDto(court.getCourtNumber(),
                LocalDateTime.of(2022, 3, 2, 17, 30),
                LocalDateTime.of(2022, 3, 2, 19, 0),
                GameType.DOUBLES, user1.getTelephoneNumber(), user1.getName());
    }

    private void assertCreateReservation(ReservationCreateDto reservationCreateDto, boolean userExists) {
        Reservation reservation = beanMapper.mapTo(reservationCreateDto, Reservation.class);
        reservation.setId(3L);
        User user = userExists ? user1 : newUser;

        when(courtService.findByCourtNumber(court.getCourtNumber())).thenReturn(court);
        if (userExists) when(userService.findByTelephoneNumber(user1.getTelephoneNumber())).thenReturn(user1);
        when(reservationService.create(any(Reservation.class))).thenReturn(reservation);

        Long newId = reservationFacade.create(reservationCreateDto);
        reservation.setCourt(court);
        reservation.setUser(user);

        verify(courtService).update(court);
        verify(userService).update(user);
        assertThat(court.getReservations()).contains(reservation1, reservation2, reservation);
        if (userExists) assertThat(user.getReservations()).contains(reservation1, reservation);
        assertThat(newId).isEqualTo(3L);
    }
}