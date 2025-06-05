package src.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "terminations")
public class Termination {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // Берет id из contract
    @JoinColumn(name = "id")  // Столбец id (и PK, и FK)
    private Contract contract;

    private LocalDate date;

    @Override
    public String toString() {
        return String.format("%s | %s", id, date);
    }
}
