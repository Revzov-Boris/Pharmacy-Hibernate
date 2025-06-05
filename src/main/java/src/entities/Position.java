package src.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "positions")
public class Position {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId  // Берет id из cheque
    @JoinColumn(name = "cheque_id")  // Столбец id (и PK, и FK)
    private Cheque cheque;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;

    private short quantity;

    @Override
    public String toString() {
        return String.format("%s | %s | %s", cheque.getId(), medicine.getId(), quantity);
    }
}
