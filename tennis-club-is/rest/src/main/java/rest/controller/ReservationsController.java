package rest.controller;

import io.mslachtova.dto.ReservationCreateDto;
import io.mslachtova.dto.ReservationDto;
import io.mslachtova.dto.ReservationTotalPriceDto;
import io.mslachtova.facade.ReservationFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rest.ApiUris;

import javax.inject.Inject;
import java.util.List;

/**
 * REST Controller for reservations
 *
 * @author Monika Slachtova
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_RESERVATIONS)
public class ReservationsController {
    final static Logger logger = LoggerFactory.getLogger(ReservationsController.class);

    @Inject
    private ReservationFacade reservationFacade;

    /**
     * Get reservations for a court with given court number curl -i -X GET
     * http://localhost:8080/rest/reservations/court_number/1
     *
     * @param courtNumber -- given court number
     * @return List<ReservationDto>
     */
    @RequestMapping(value = "court_number/{courtNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ReservationDto> getReservationsByCourtNumber(@PathVariable("courtNumber") int courtNumber) {
        logger.info("rest getReservationsByCourtNumber({})", courtNumber);
        return reservationFacade.getReservationsByCourtNumber(courtNumber);
    }

    /**
     * Get reservations for a user with given telephone number curl -i -X GET
     * http://localhost:8080/rest/reservations/telephone_number/745558238
     *
     * @param telephoneNumber -- given telephone number
     * @return List<ReservationDto>
     */
    @RequestMapping(value = "telephone_number/{telephoneNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<ReservationDto> getReservationsByTelephoneNumber(@PathVariable("telephoneNumber") String telephoneNumber) {
        logger.info("rest getReservationsByTelephoneNumber({})", telephoneNumber);
        return reservationFacade.getReservationsByTelephoneNumber(telephoneNumber);
    }

    /**
     * Create a new reservation by POST method
     * curl -X POST -i -H "Content-Type: application/json" --data
     * '{"courtNumber":"1", "from":"2022-01-05 10:30", "to":"2022-01-05 11:30"}, "gameType":"SINGLES",
     * "telephoneNumber":"758447228", "name":"Jane Smith"}'
     * http://localhost:8080/rest/reservations/create
     *
     * @param reservationCreateDto -- object with required field for creation
     * @return ReservationTotalPriceDto including reservation info and price
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ReservationTotalPriceDto createReservation(@RequestBody ReservationCreateDto reservationCreateDto) {
        logger.info("rest createReservation()");

        try {
            Long id = reservationFacade.create(reservationCreateDto);
            return reservationFacade.getTotalPrice(id);
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
