package thelaborseekers.jobhubapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Ofertantes")
public class Ofertante extends User{

    @OneToMany(mappedBy = "ofertante",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<JobOffer> jobOffers;
}
