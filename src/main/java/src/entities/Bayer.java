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
@Table(name = "bayers")
public class Bayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "second_name")
    private String secondName;
    @Column(name = "third_name")
    private String thirdName;
    private String gender;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s | %s ",
                id, firstName, secondName, thirdName, gender, phoneNumber, email);
    }

}
