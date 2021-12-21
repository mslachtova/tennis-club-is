package dto;

import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class ReservationTotalPrice {
    private ReservationDto reservationDto;
    private double totalPrice;

    public ReservationDto getReservationDto() {
        return reservationDto;
    }

    public void setReservationDto(ReservationDto reservationDto) {
        this.reservationDto = reservationDto;
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
        if (!(o instanceof ReservationTotalPrice)) return false;
        ReservationTotalPrice that = (ReservationTotalPrice) o;
        return getReservationDto().equals(that.getReservationDto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReservationDto());
    }

    @Override
    public String toString() {
        return "ReservationTotalPrice{" +
                "reservationDto=" + reservationDto +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
