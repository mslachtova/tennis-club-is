package io.mslachtova.dao;

import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.User;

/**
 * Class including methods used by Test classes
 *
 * @author Monika Slachtova
 */
public class TestHelper {
    static CourtSurface getGrassCourtSurface() {
        return new CourtSurface("grass", 200.0);
    }

    static CourtSurface getHardCourtSurface() {
        return new CourtSurface("hard", 180.0);
    }

    static User getAlice() {
        return new User("745558238", "Alice Smith");
    }

    static User getBob() {
       return new User("766456991", "Bob Taylor");
    }
}
