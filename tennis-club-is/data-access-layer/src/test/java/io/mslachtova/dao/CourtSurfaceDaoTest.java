package io.mslachtova.dao;

import io.mslachtova.PersistenceConfig;
import io.mslachtova.entity.CourtSurface;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Monika Slachtova
 */
@ContextConfiguration(classes = PersistenceConfig.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
class CourtSurfaceDaoTest {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private CourtSurfaceDao courtSurfaceDao;

    private CourtSurface courtSurface1;
    private CourtSurface courtSurface2;

    @BeforeEach
    void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            courtSurface1 = new CourtSurface();
            courtSurface1.setSurfaceType("grass");
            courtSurface1.setPrice(200.0);
            em.persist(courtSurface1);

            courtSurface2 = new CourtSurface();
            courtSurface2.setSurfaceType("hard");
            courtSurface2.setPrice(180.0);
            em.persist(courtSurface2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    void create() {
        CourtSurface courtSurface = new CourtSurface();
        courtSurface.setSurfaceType("clay");
        courtSurface.setPrice(160.0);
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
            CourtSurface courtSurface = new CourtSurface();
            courtSurface.setPrice(160.0);
            courtSurfaceDao.create(courtSurface);
        });
    }

    @Test
    void createNullPrice() {
        assertThrows(DataAccessException.class, () -> {
            CourtSurface courtSurface = new CourtSurface();
            courtSurface.setSurfaceType("clay");
            courtSurfaceDao.create(courtSurface);
        });
    }

    @Test
    void createExistingSurfaceType() {
        assertThrows(DataAccessException.class, () -> {
            CourtSurface courtSurface = new CourtSurface();
            courtSurface.setSurfaceType("hard");
            courtSurface.setPrice(160.0);
            courtSurfaceDao.create(courtSurface);
        });
    }

    @Test
    void findById() {
        CourtSurface foundCourtSurface = courtSurfaceDao.findById(courtSurface1.getId());
        assertThat(foundCourtSurface).isEqualTo(courtSurface1);
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
}