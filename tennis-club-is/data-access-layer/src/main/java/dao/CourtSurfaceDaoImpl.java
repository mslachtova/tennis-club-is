package dao;

import entity.CourtSurface;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Monika Slachtova
 */
@Repository
public class CourtSurfaceDaoImpl implements CourtSurfaceDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(CourtSurface courtSurface) {
        em.persist(courtSurface);
    }

    @Override
    public CourtSurface findById(Long id) {
        return em.find(CourtSurface.class, id);
    }

    @Override
    public List<CourtSurface> findAll() {
        return em.createQuery("select c from CourtSurface c ", CourtSurface.class).getResultList();
    }
}
