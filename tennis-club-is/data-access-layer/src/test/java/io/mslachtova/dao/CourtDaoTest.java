package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static io.mslachtova.dao.TestHelper.getGrassCourtSurface;
import static io.mslachtova.dao.TestHelper.getHardCourtSurface;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertThrows;


/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class CourtDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private CourtDao courtDao;

    private CourtSurface courtSurface1;
    private CourtSurface courtSurface2;

    private Court court1;
    private Court court2;

    @BeforeClass
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            courtSurface1 = getGrassCourtSurface();
            em.persist(courtSurface1);

            courtSurface2 = getHardCourtSurface();
            em.persist(courtSurface2);

            court1 = new Court(courtSurface1);
            em.persist(court1);

            court2 = new Court(courtSurface2);
            em.persist(court2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        Court court = new Court();
        court.setCourtSurface(courtSurface2);
        courtDao.create(court);
        assertThat(courtDao.findById(court.getId())).isEqualTo(court);
    }

    @Test
    void createNullCourtSurface() {
        assertThrows(DataAccessException.class, () -> {
           Court court = new Court();
           courtDao.create(court);
        });
    }

    @Test
    void findById() {
        assertThat(courtDao.findById(court1.getId())).isEqualTo(court1);
    }

    @Test
    void findAll() {
        List<Court> foundCourts = courtDao.findAll();
        assertThat(foundCourts.size()).isEqualTo(2);
        assertThat(foundCourts).contains(court1, court2);
    }

    @Test
    void findByCourtNumber() {
        assertThat(courtDao.findByCourtNumber(court2.getCourtNumber())).isEqualTo(court2);
    }

    @Test
    void findByNonExistingCourtNumber() {
        assertThat(courtDao.findByCourtNumber(6)).isNull();
    }

    @Test
    void update() {
        court1.setCourtSurface(courtSurface2);
        courtDao.update(court1);
        assertThat(courtDao.findById(court1.getId()).getCourtSurface()).isEqualTo(courtSurface2);
    }
}