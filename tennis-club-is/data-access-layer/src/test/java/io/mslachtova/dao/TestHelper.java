package io.mslachtova.dao;

import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.Reservation;
import io.mslachtova.entity.User;
import io.mslachtova.enums.GameType;

import java.time.LocalDateTime;

/**
 * Class including methods used by Test classes
 *
 * @author Monika Slachtova
 */
public class TestHelper {
    static CourtSurface getGrassCourtSurface() {
        CourtSurface courtSurface = new CourtSurface("grass", 200.0);
        return courtSurface;
    }

    static CourtSurface getHardCourtSurface() {
        CourtSurface courtSurface = new CourtSurface("hard", 180.0);
        return courtSurface;
    }

    static User getAlice() {
        User user = new User("745558238", "Alice Smith");
        return user;
    }

    static User getBob() {
        User user = new User("766456991", "Bob Taylor");
        return user;
    }
}
