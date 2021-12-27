package io.mslachtova.dao;

import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;
import io.mslachtova.entity.User;

/**
 * Class including methods used by Test classes
 *
 * @author Monika Slachtova
 */
public class TestHelper {
    static CourtSurface getGrassCourtSurface() {
        CourtSurface courtSurface = new CourtSurface();
        courtSurface.setSurfaceType("grass");
        courtSurface.setPrice(200.0);
        return courtSurface;
    }

    static CourtSurface getHardCourtSurface() {
        CourtSurface courtSurface = new CourtSurface();
        courtSurface.setSurfaceType("hard");
        courtSurface.setPrice(180.0);
        return courtSurface;
    }

    static Court getCourtWithGivenSurface(CourtSurface courtSurface) {
        Court court = new Court();
        court.setCourtSurface(courtSurface);
        return court;
    }

    static User getAlice() {
        User user = new User();
        user.setTelephoneNumber("745558238");
        user.setName("Alice Smith");
        return user;
    }

    static User getBob() {
        User user = new User();
        user.setTelephoneNumber("766456991");
        user.setName("Bob Taylor");
        return user;
    }
}
