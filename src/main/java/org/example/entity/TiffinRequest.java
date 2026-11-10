package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "tiffin_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TiffinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private LocalDate requestDate;
    private int quantity;
    private String deliveryAddress;
    @Column(nullable = false)
    private String status;



}
