package io.mslachtova.dto;

import java.util.List;
import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class CourtDto {
    private Long id;
    private int courtNumber;
    private CourtSurfaceDto courtSurface;
    private List<ReservationDto> reservations;

    public CourtDto() {
    }

    public CourtDto(CourtSurfaceDto courtSurface) {
        this.courtSurface = courtSurface;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(int courtNumber) {
        this.courtNumber = courtNumber;
    }

    public CourtSurfaceDto getCourtSurface() {
        return courtSurface;
    }

    public void setCourtSurface(CourtSurfaceDto courtSurface) {
        this.courtSurface = courtSurface;
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
        if (!(o instanceof CourtDto)) return false;
        CourtDto courtDto = (CourtDto) o;
        return getCourtNumber() == courtDto.getCourtNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourtNumber());
    }

    @Override
    public String toString() {
        return "CourtDto{" +
                "courtNumber=" + courtNumber +
                ", courtSurface=" + courtSurface +
                '}';
    }
}
