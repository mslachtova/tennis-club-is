package io.mslachtova.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Class represents a tennis court
 *
 * @author Monika Slachtova
 */
@Entity
public class Court {
    private static int courtCounter = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @NotNull
    private int courtNumber;

    @ManyToOne
    @NotNull
    private CourtSurface courtSurface;

    @OneToMany(mappedBy = "reservation")
    private List<Reservation> reservations;

    public Court() {
        courtNumber = assignCourtNumber();
    }

    public Court(@NotNull CourtSurface courtSurface) {
        this.courtSurface = courtSurface;
        courtNumber = assignCourtNumber();
    }

    private static int assignCourtNumber() {
        courtCounter++;
        return courtCounter;
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

    public CourtSurface getCourtSurface() {
        return courtSurface;
    }

    public void setCourtSurface(CourtSurface courtSurface) {
        this.courtSurface = courtSurface;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        reservation.setCourt(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Court)) return false;
        Court court = (Court) o;
        return getCourtNumber() == court.getCourtNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourtNumber());
    }
}
