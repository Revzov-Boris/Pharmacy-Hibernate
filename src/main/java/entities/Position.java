package entities;

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
    @EmbeddedId
    private PositionId positionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chequeId")
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    private Cheque cheque;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicineId")
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    private short quantity;

    @Override
    public String toString() {
        return String.format("%s | %s | %s", positionId.getChequeId(), positionId.getMedicineId(), quantity);
    }
}
