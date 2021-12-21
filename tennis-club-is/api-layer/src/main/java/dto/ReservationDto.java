package dto;

import enums.GameType;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class ReservationDto {
    private Long id;
    private CourtDto court;
    private LocalDate from;
    private LocalDate to;
    private GameType gameType;
    private UserDto user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourtDto getCourt() {
        return court;
    }

    public void setCourt(CourtDto court) {
        this.court = court;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationDto)) return false;
        ReservationDto that = (ReservationDto) o;
        return getCourt().equals(that.getCourt()) && getFrom().equals(that.getFrom()) && getTo().equals(that.getTo())
                && getUser().equals(that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourt(), getFrom(), getTo(), getUser());
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "court=" + court +
                ", from=" + from +
                ", to=" + to +
                ", gameType=" + gameType +
                ", user=" + user +
                '}';
    }
}
