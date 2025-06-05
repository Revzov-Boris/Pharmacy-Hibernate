package src.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "workers")
public class Worker {
    @Id
    @Column(name = "id")
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
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String education;


    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s | %s | %s | %s",
                id, firstName, secondName, thirdName, gender, phoneNumber, email, dateOfBirth, education);
    }
}
