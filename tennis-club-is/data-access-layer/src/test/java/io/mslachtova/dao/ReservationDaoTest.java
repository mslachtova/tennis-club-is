package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import io.mslachtova.enums.GameType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.time.LocalDateTime;
import java.util.List;

import static io.mslachtova.dao.TestHelper.getAlice;
import static io.mslachtova.dao.TestHelper.getBob;
import static io.mslachtova.dao.TestHelper.getGrassCourtSurface;
import static io.mslachtova.dao.TestHelper.getHardCourtSurface;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class ReservationDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private ReservationDao reservationDao;

    private Court court1;
    private Court court2;

    private User user1;
    private User user2;

    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeClass
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            CourtSurface courtSurface1 = getGrassCourtSurface();
            CourtSurface courtSurface2 = getHardCourtSurface();

            court1 = new Court(courtSurface1);
            court2 = new Court(courtSurface2);

            user1 = getAlice();
            user2 = getBob();

            reservation1 = new Reservation(LocalDateTime.of(2022, 1, 5, 10, 30),
                    LocalDateTime.of(2022, 1, 5, 11, 30), GameType.DOUBLES);
            reservation2 = new Reservation(LocalDateTime.of(2022, 1, 7, 10, 30),
                    LocalDateTime.of(2022, 1, 7, 12, 0), GameType.SINGLES);

            court1.addReservation(reservation1);
            user2.addReservation(reservation1);
            court2.addReservation(reservation2);
            user1.addReservation(reservation2);

            em.persist(courtSurface1);
            em.persist(courtSurface2);
            em.persist(court1);
            em.persist(court2);
            em.persist(user1);
            em.persist(user2);
            em.persist(reservation1);
            em.persist(reservation2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterClass
    public void tearDown() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("delete from Reservation").executeUpdate();
            em.createQuery("delete from Court").executeUpdate();
            em.createQuery("delete from CourtSurface").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test(dependsOnMethods = "findById")
    void create() {
        Reservation reservation = new Reservation(LocalDateTime.of(2022, 1, 10, 10, 30),
                LocalDateTime.of(2022, 2, 1, 9, 0), GameType.SINGLES);
        court2.addReservation(reservation);
        user1.addReservation(reservation);
        reservationDao.create(reservation);
        assertThat(reservationDao.findById(reservation.getId())).isEqualTo(reservation);
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

    @Test(dependsOnMethods = "findById")
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