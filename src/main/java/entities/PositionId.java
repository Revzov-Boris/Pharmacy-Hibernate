package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Embeddable
public class PositionId implements Serializable {
    @Column(name = "cheque_id")
    private Long chequeId;

    @Column(name = "medicine_id")
    private Long medicineId;

    @Override
    public String toString() {
        return String.format("%s | %s", chequeId, medicineId);
    }
}
