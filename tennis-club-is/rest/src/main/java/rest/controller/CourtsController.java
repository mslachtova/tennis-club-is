package rest.controller;

import dto.CourtDto;
import facade.CourtFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rest.ApiUris;

import javax.inject.Inject;
import java.util.List;

/**
 * REST Controller for courts
 *
 * @author Monika Slachtova
 */
@RestController
@RequestMapping(ApiUris.ROOT_URI_COURTS)
public class CourtsController {
    final static Logger logger = LoggerFactory.getLogger(CourtsController.class);

    @Inject
    private CourtFacade courtFacade;

    /**
     * Get list of courts curl -i -X GET
     * http://localhost:8080/rest/wines
     *
     * @return CourtDto
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<CourtDto> getCourts() {
        logger.info("rest getCourts()");
        return courtFacade.findAll();
    }
}
