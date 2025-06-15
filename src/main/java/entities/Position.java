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
    private PositionId positionId = new PositionId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("chequeId")
    @JoinColumn(name = "cheque_id", referencedColumnName = "id")
    private Cheque cheque;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicineId")
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    private short quantity;

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
        if (this.positionId == null) {
            this.positionId = new PositionId();
        }
        this.positionId.setChequeId(cheque.getId());
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
        if (this.positionId == null) {
            this.positionId = new PositionId();
        }
        this.positionId.setMedicineId(medicine.getId());
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s", positionId.getChequeId(), positionId.getMedicineId(), quantity);
    }
}
