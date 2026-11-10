package org.example.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String contactNumber;

    @Column(unique = true, nullable = false)
    private String emailId;

    private String address;

    @Column(nullable = false)
    private String role; // "USER" or "ADMIN"
}
