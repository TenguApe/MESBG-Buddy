package de.tenguape.mesbg.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String faction;

    private int mightPointsMax;
    private int mightPointsCurrent;

    private int willPointsMax;
    private int willPointsCurrent;

    private int fatePointsMax;
    private int fatePointsCurrent;

    @Column(length = 1000)
    private String specialRules;
}