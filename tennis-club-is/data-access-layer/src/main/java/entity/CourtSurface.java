package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Class representing a court surface type with its corresponding price
 *
 * @author Monika Slachtova
 */
@Entity
public class CourtSurface {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String surfaceType;

    @NotNull
    private double price;

    public CourtSurface() {
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
        if (!(o instanceof CourtSurface)) return false;
        CourtSurface that = (CourtSurface) o;
        return getSurfaceType().equals(that.getSurfaceType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSurfaceType());
    }
}
