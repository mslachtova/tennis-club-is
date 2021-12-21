package dto;

import enums.GameType;

import java.time.LocalDate;

/**
 * @author Monika Slachtova
 */
public class ReservationCreateDto {
    private Long id;
    private int courtNumber;
    private LocalDate from;
    private LocalDate to;
    private GameType gameType;
    private String telephoneNumber;
    private String name;
}
