package rest.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Monika Slachtova
 */
@JsonIgnoreProperties({ "reservations"})
public class UserDtoMixin {
}
