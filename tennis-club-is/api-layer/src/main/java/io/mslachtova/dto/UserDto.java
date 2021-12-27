package io.mslachtova.dto;

import java.util.List;
import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class UserDto {
    private Long id;
    private String telephoneNumber;
    private String name;
    private List<ReservationDto> reservations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return getTelephoneNumber().equals(userDto.getTelephoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTelephoneNumber());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "telephoneNumber='" + telephoneNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
