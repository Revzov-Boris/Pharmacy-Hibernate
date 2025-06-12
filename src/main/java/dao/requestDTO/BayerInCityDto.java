package dao.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class BayerInCityDto {
    private Long id;
    private Long bayerId;
    private Long pharmacyId;
    private LocalDate date;
    private BigDecimal sumPrice;

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s", id, bayerId, pharmacyId, date, sumPrice);
    }
}
