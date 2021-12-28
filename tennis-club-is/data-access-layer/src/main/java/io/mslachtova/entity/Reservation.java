package io.mslachtova.entity;

import io.mslachtova.enums.GameType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class representing a reservation of a tennis court
 *
 * @author Monika Slachtova
 */
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Court court;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GameType gameType;

    @ManyToOne
    private User user;

    public Reservation() {
    }

    public Reservation(Court court, @NotNull LocalDateTime from, @NotNull LocalDateTime to, @NotNull GameType gameType,
                       User user) {
        this.court = court;
        this.from = from;
        this.to = to;
        this.gameType = gameType;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reservation)) return false;
        Reservation that = (Reservation) o;
        return getCourt().equals(that.getCourt()) && getFrom().equals(that.getFrom()) && getTo().equals(that.getTo()) && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourt(), getFrom(), getTo(), getUser());
    }
}
