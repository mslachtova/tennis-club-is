package service;

import enums.GameType;
import io.mslachtova.dao.ReservationDao;
import io.mslachtova.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monika Slachtova
 */
@Service
public class ReservationServiceImpl implements ReservationService {
    public static double DOUBLES_PRICE_MULTIPLICATION = 1.5;

    @Autowired
    private ReservationDao reservationDao;

    @Override
    public void create(Reservation reservation) {
        reservationDao.create(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    @Override
    public double getTotalPrice(Reservation reservation) {
        double totalPrice = reservation.getCourt().getCourtSurface().getPrice();
        return reservation.getGameType() == GameType.DOUBLES ? totalPrice * DOUBLES_PRICE_MULTIPLICATION : totalPrice;
    }
}
