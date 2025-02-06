package caugarde.vote.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RentalGear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEAR", nullable = false)
    private Gear gear;

    @Column(name = "RENTAL_DATE", nullable = false)
    private LocalDateTime rentalDate;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "LATE_FEE", nullable = false)
    private int lateFee = 0;

    @Column(name = "RETURNED_AT")
    private LocalDateTime returnedAt;

    private RentalGear(Student student, Gear gear) {
        this.student = student;
        this.gear = gear;
        this.rentalDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now()
                .plusDays(3)
                .toLocalDate()
                .atTime(23, 59);
    }

    public static RentalGear of(Student student, Gear gear) {
        return new RentalGear(student, gear);
    }

    public void imposeLateFee(){
        this.lateFee += 2000;
    }

    public void returnGear(){
        this.returnedAt = LocalDateTime.now();
    }

}
