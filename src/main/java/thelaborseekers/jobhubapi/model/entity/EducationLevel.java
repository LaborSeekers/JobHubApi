package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import thelaborseekers.jobhubapi.model.enums.EduLevel;

@Data
@Entity
@Table(name = "Education_Level")
public class EducationLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name",nullable = false)
    private EduLevel name;
}
