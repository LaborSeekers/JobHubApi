package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "FavoriteJobOffers")
@IdClass(FavoriteJobOffersPK.class)
public class FavoriteJobOffers {
    @Id
    @ManyToOne
    @JoinColumn(name = "Postulante_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_favoritejoboffers_postulante"))
    private Postulante postulante;
    @Id
    @ManyToOne
    @JoinColumn(name = "JobOffer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_favoritejoboffers_offer"))
    private JobOffer jobOffer;
}
