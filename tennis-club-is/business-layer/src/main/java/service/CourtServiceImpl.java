package service;

import io.mslachtova.dao.CourtDao;
import io.mslachtova.entity.Court;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
public class CourtServiceImpl implements CourtService {
    @Autowired
    private CourtDao courtDao;

    @Override
    public void create(Court court) {
        courtDao.create(court);
    }

    @Override
    public Court findById(Long id) {
        return courtDao.findById(id);
    }

    @Override
    public List<Court> findAll() {
        return courtDao.findAll();
    }

    @Override
    public Court findByCourtNumber(int courtNumber) {
        return courtDao.findByCourtNumber(courtNumber);
    }

    @Override
    public void update(Court court) {
        courtDao.update(court);
    }
}
