package io.mslachtova.dto;

import java.util.Objects;

/**
 * @author Monika Slachtova
 */
public class CourtSurfaceDto {
    private Long id;
    private String surfaceType;
    private double price;

    public CourtSurfaceDto() {
    }

    public CourtSurfaceDto(String surfaceType, double price) {
        this.surfaceType = surfaceType;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurfaceType() {
        return surfaceType;
    }

    public void setSurfaceType(String surfaceType) {
        this.surfaceType = surfaceType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourtSurfaceDto)) return false;
        CourtSurfaceDto that = (CourtSurfaceDto) o;
        return getSurfaceType().equals(that.getSurfaceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurfaceType());
    }

    @Override
    public String toString() {
        return "CourtSurfaceDto{" +
                "surfaceType='" + surfaceType + '\'' +
                ", price=" + price +
                '}';
    }
}
