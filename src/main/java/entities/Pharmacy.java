package entities;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "pharmacys")
public class Pharmacy {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    @Column(name = "storage_square")
    private short storageSquare;
    @Column(name = "hall_square")
    private short hallSquare;


    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s", id, name, phoneNumber, address, storageSquare, hallSquare);
    }
}
