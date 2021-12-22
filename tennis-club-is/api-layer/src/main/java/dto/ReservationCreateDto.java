package dto;

import enums.GameType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class ReservationCreateDto {
    private Long id;
    private int courtNumber;
    private LocalDateTime from;
    private LocalDateTime to;
    private GameType gameType;
    private String telephoneNumber;
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReservationCreateDto)) return false;
        ReservationCreateDto that = (ReservationCreateDto) o;
        return getCourtNumber() == that.getCourtNumber() && getFrom().equals(that.getFrom()) && getTo().equals(that.getTo()) && getTelephoneNumber().equals(that.getTelephoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourtNumber(), getFrom(), getTo(), getTelephoneNumber());
    }
}
