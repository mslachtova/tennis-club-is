package io.mslachtova.dto;

import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class ReservationTotalPriceDto {
    private ReservationDto reservation;
    private double totalPrice;

    public ReservationDto getReservationDto() {
        return reservation;
    }

    public void setReservation(ReservationDto reservation) {
        this.reservation = reservation;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationTotalPriceDto)) return false;
        ReservationTotalPriceDto that = (ReservationTotalPriceDto) o;
        return getReservationDto().equals(that.getReservationDto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReservationDto());
    }

    @Override
    public String toString() {
        return "ReservationTotalPrice{" +
                "reservationDto=" + reservation +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
