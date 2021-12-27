package facade;

import dto.ReservationCreateDto;
import dto.ReservationDto;
import dto.ReservationTotalPriceDto;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.BeanMapper;
import service.CourtService;
import service.ReservationService;
import service.UserService;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade {
    @Autowired
    private CourtService courtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public Long create(ReservationCreateDto reservation) {
        Court court = courtService.findByCourtNumber(reservation.getCourtNumber());
        checkCourt(court, reservation);
        User user = userService.findByTelephoneNumber(reservation.getTelephoneNumber());
        checkUser(user, reservation);
        Reservation r = beanMapper.mapTo(reservation, Reservation.class);
        court.addReservation(r);
        user.addReservation(r);
        courtService.update(court);
        userService.update(user);
        Reservation newReservation = reservationService.create(r);
        return newReservation.getId();
    }

    @Override
    public ReservationDto findById(Long id) {
        return beanMapper.mapTo(reservationService.findById(id), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> findAll() {
        return beanMapper.mapTo(reservationService.findAll(), ReservationDto.class);
    }

    @Override
    public ReservationTotalPriceDto getTotalPrice(long id) {
        Reservation reservation = reservationService.findById(id);
        if (reservation == null)
            throw new IllegalArgumentException("Reservation with id " + id + " not found");
        ReservationTotalPriceDto reservationTotalPriceDto = new ReservationTotalPriceDto();
        reservationTotalPriceDto.setReservation(beanMapper.mapTo(reservation, ReservationDto.class));
        reservationTotalPriceDto.setTotalPrice(reservationService.getTotalPrice(reservation));
        return reservationTotalPriceDto;
    }

    @Override
    public void update(ReservationDto reservation) {
        reservationService.update(beanMapper.mapTo(reservation, Reservation.class));
    }

    @Override
    public List<ReservationDto> getReservationsByCourtNumber(int courtNumber) {
        Court court = courtService.findByCourtNumber(courtNumber);
        return court == null ? null : beanMapper.mapTo(court.getReservations(), ReservationDto.class);
    }

    @Override
    public List<ReservationDto> getReservationsByTelephoneNumber(String telephoneNumber) {
        User user = userService.findByTelephoneNumber(telephoneNumber);
        return user == null ? null : beanMapper.mapTo(user.getReservations(), ReservationDto.class);
    }

    private void checkCourt(Court court, ReservationCreateDto reservation) {
        if (court == null)
            throw new IllegalArgumentException("Court with court number " + reservation.getCourtNumber()
                    + " not found.");
        for (Reservation r : court.getReservations()) {
            if (!reservation.getTo().isAfter(reservation.getFrom()))
                throw new IllegalArgumentException(reservation.getTo() + " is not after " + reservation.getFrom());
            if ((reservation.getFrom().compareTo(r.getFrom()) < 0 && reservation.getTo().compareTo(r.getFrom()) > 0)
                    || (reservation.getFrom().compareTo(r.getFrom()) >= 0 && reservation.getFrom().compareTo(r.getTo()) < 0))
                throw new IllegalArgumentException("The time interval has already been reserved.");
        }
    }

    private void checkUser(User user, ReservationCreateDto reservation) {
        if (user != null && !user.getName().equals(reservation.getName()))
            throw new IllegalArgumentException("Telephone number " + reservation.getTelephoneNumber()
                    + "has already been attached to name " + user.getName() + ".");
        if (user == null) {
            user = new User();
            user.setTelephoneNumber(reservation.getTelephoneNumber());
            user.setName(reservation.getName());
            userService.create(user);
        }
    }
}
