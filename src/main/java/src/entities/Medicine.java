package src.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "is_prescription")
    private boolean isPrescription;
    @Column(name = "is_dietary_supplements")
    private boolean isDietarySupplements;
    private String shape;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%d | %s | %b | %b | %s | %s", id, name, isPrescription, isDietarySupplements, shape, price);
    }
}
