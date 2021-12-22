package sample;

import enums.GameType;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import service.CourtService;
import service.CourtSurfaceService;
import service.ReservationService;
import service.UserService;

import java.time.LocalDateTime;
import java.util.LinkedList;

/**
 * @author Monika Slachtova
 */
@Component
@Transactional
public class SampleDataLoadingFacadeImpl implements SampleDataLoadingFacade {
    final static Logger log = LoggerFactory.getLogger(SampleDataLoadingFacadeImpl.class);

    @Autowired
    private CourtSurfaceService courtSurfaceService;

    @Autowired
    private CourtService courtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void loadData() {
        CourtSurface hard = courtSurface("hard", 180.0);
        CourtSurface clay = courtSurface("clay", 160.0);
        CourtSurface grass = courtSurface("grass", 200.0);
        log.info("Loaded court surfaces");

        LinkedList<Court> courts = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            courts.add(court(hard));
            courts.add(court(clay));
            courts.add(court(grass));
        }
        log.info("Loaded courts");

        User alice = user("745558238", "Alice Smith");
        User bob = user("766456991", "Bob Taylor");
        log.info("Loaded users");

        reservation(courts.get(1),
                LocalDateTime.of(2022, 1, 5, 10, 30),
                LocalDateTime.of(2022, 1, 5, 11, 30),
                GameType.SINGLES,
                alice);
        reservation(courts.getLast(),
                LocalDateTime.of(2022, 1, 7, 10, 30),
                LocalDateTime.of(2022, 1, 5, 12, 0),
                GameType.DOUBLES,
                bob);
        log.info("Loaded reservations");
    }

    private Reservation reservation(Court court, LocalDateTime from, LocalDateTime to, GameType gameType, User user) {
        Reservation reservation = new Reservation();
        reservation.setFrom(from);
        reservation.setTo(to);
        reservation.setGameType(gameType);
        court.addReservation(reservation);
        user.addReservation(reservation);
        reservationService.create(reservation);
        courtService.update(court);
        userService.update(user);
        return reservation;
    }

    private User user(String telephoneNumber, String name) {
        User user = new User();
        user.setTelephoneNumber(telephoneNumber);
        user.setName(name);
        userService.create(user);
        return user;
    }

    private Court court(CourtSurface courtSurface) {
        Court court = new Court();
        court.setCourtSurface(courtSurface);
        courtService.create(court);
        return court;
    }

    private CourtSurface courtSurface(String type, double price) {
        CourtSurface courtSurface = new CourtSurface();
        courtSurface.setSurfaceType(type);
        courtSurface.setPrice(price);
        courtSurfaceService.create(courtSurface);
        return courtSurface;
    }
}
