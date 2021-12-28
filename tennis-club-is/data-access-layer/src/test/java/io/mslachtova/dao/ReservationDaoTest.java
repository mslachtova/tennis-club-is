package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import io.mslachtova.enums.GameType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import java.time.LocalDateTime;
import java.util.List;

import static io.mslachtova.dao.TestHelper.getAlice;
import static io.mslachtova.dao.TestHelper.getBob;
import static io.mslachtova.dao.TestHelper.getCourtWithGivenSurface;
import static io.mslachtova.dao.TestHelper.getGrassCourtSurface;
import static io.mslachtova.dao.TestHelper.getHardCourtSurface;
import static io.mslachtova.dao.TestHelper.getReservationWithGivenParameters;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
class ReservationDaoTest {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private ReservationDao reservationDao;

    private CourtSurface courtSurface1;
    private CourtSurface courtSurface2;

    private Court court1;
    private Court court2;

    private User user1;
    private User user2;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            courtSurface1 = getGrassCourtSurface();
            em.persist(courtSurface1);
            courtSurface2 = getHardCourtSurface();
            em.persist(courtSurface2);

            court1 = getCourtWithGivenSurface(courtSurface1);
            em.persist(court1);
            court2 = getCourtWithGivenSurface(courtSurface2);
            em.persist(court2);

            user1 = getAlice();
            em.persist(user1);
            user2 = getBob();
            em.persist(user2);

            reservation1 = getReservationWithGivenParameters(court1,
                    LocalDateTime.of(2022, 1, 5, 10, 30),
                    LocalDateTime.of(2022, 1, 5, 11, 30),
                    GameType.DOUBLES, user2);
            em.persist(reservation1);
            reservation2 = getReservationWithGivenParameters(court2,
                    LocalDateTime.of(2022, 1, 7, 10, 30),
                    LocalDateTime.of(2022, 1, 7, 12, 0),
                    GameType.SINGLES, user1);
            em.persist(reservation2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        Reservation reservation = getReservationWithGivenParameters(court2,
                LocalDateTime.of(2022, 1, 10, 10, 30),
                LocalDateTime.of(2022, 2, 1, 9, 0),
                GameType.SINGLES, user1);
        reservationDao.create(reservation);
        assertThat(reservationDao.findById(reservation.getId())).isEqualTo(reservation);
    }

    @Test
    void createNullFrom() {
        assertThrows(DataAccessException.class, () -> {
           reservationDao.create(getReservationWithGivenParameters(court1,null,
                   LocalDateTime.of(2022, 1, 6, 18, 0),
                   GameType.DOUBLES, user2));
        });
    }

    @Test
    void createNullTo() {
        assertThrows(DataAccessException.class, () -> {
            reservationDao.create(getReservationWithGivenParameters(court1,
                    LocalDateTime.of(2022, 1, 6, 16, 30),
                    null, GameType.DOUBLES, user2));
        });
    }

    @Test
    void createNullGameType() {
        assertThrows(DataAccessException.class, () -> {
            reservationDao.create(getReservationWithGivenParameters(court1,
                    LocalDateTime.of(2022, 1, 6, 16, 30),
                    LocalDateTime.of(2022, 1, 6, 18, 0),
                    null, user2));
        });
    }

    @Test
    void findById() {
        assertThat(reservationDao.findById(reservation1.getId())).isEqualTo(reservation1);
    }

    @Test
    void findAll() {
        List<Reservation> foundReservations = reservationDao.findAll();
        assertThat(foundReservations.size()).isEqualTo(2);
        assertThat(foundReservations).contains(reservation2, reservation1);
    }

    @Test
    void update() {
        LocalDateTime newTo = LocalDateTime.of(2022, 1, 7, 12, 30);
        reservation2.setTo(newTo);
        reservation2.setGameType(GameType.DOUBLES);
        reservationDao.update(reservation2);
        Reservation reservation = reservationDao.findById(reservation2.getId());
        assertThat(reservation.getTo()).isEqualTo(newTo);
        assertThat(reservation.getGameType()).isEqualTo(GameType.DOUBLES);
    }
}