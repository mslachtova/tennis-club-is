package io.mslachtova.dao;

import io.mslachtova.entity.Court;
import io.mslachtova.entity.CourtSurface;

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
}
