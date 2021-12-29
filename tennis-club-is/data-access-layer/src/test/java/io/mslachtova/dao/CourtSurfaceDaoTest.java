package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
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
public class CourtSurfaceDaoTest extends AbstractTestNGSpringContextTests {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private CourtSurfaceDao courtSurfaceDao;

    private CourtSurface courtSurface1;
    private CourtSurface courtSurface2;

    @BeforeClass
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            courtSurface1 = getGrassCourtSurface();
            em.persist(courtSurface1);

            courtSurface2 = getHardCourtSurface();
            em.persist(courtSurface2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        CourtSurface courtSurface = new CourtSurface("clay", 160.0);
        courtSurfaceDao.create(courtSurface);
        assertThat(courtSurfaceDao.findById(courtSurface.getId())).isEqualTo(courtSurface);
    }

    @Test
    void createNullEntity() {
        assertThrows(DataAccessException.class, () -> courtSurfaceDao.create(null));
    }

    @Test
    void createNullSurfaceType() {
        assertThrows(DataAccessException.class, () -> {
            CourtSurface courtSurface = new CourtSurface(null, 160.0);
            courtSurfaceDao.create(courtSurface);
        });
    }

    @Test
    void createExistingSurfaceType() {
        assertThrows(DataAccessException.class, () -> {
            CourtSurface courtSurface = new CourtSurface("hard", 160.0);
            courtSurfaceDao.create(courtSurface);
        });
    }

    @Test
    void createExistingEntity() {
        assertThrows(DataAccessException.class, () -> courtSurfaceDao.create(courtSurface1));
    }

    @Test
    void findById() {
        CourtSurface foundCourtSurface = courtSurfaceDao.findById(courtSurface1.getId());
        assertThat(foundCourtSurface).isEqualTo(courtSurface1);
    }

    @Test
    void findByIdNullId() {
        assertThrows(DataAccessException.class, () -> courtSurfaceDao.findById(null));
    }

    @Test
    void findByIdNonExistingId() {
        assertThrows(DataAccessException.class, () -> courtSurfaceDao.findById(5L));
    }

    @Test
    void findAll() {
        List<CourtSurface> foundCourtSurfaces = courtSurfaceDao.findAll();
        assertThat(foundCourtSurfaces.size()).isEqualTo(2);
        assertThat(foundCourtSurfaces).contains(courtSurface1, courtSurface2);
    }

    @Test
    void update() {
        String newCourtSurface = "green";
        courtSurface1.setSurfaceType(newCourtSurface);
        courtSurfaceDao.update(courtSurface2);
        assertThat(courtSurfaceDao.findById(courtSurface1.getId()).getSurfaceType()).isEqualTo(newCourtSurface);
    }
}