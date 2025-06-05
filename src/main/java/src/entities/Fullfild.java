package src.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "fulfilleds")
public class Fullfild {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // Берет id из order
    @JoinColumn(name = "id")  // Столбец id (и PK, и FK)
    private Order order;

    private LocalDate date;

    @Override
    public String toString() {
        return String.format("%s | %s", id, date);
    }
}
