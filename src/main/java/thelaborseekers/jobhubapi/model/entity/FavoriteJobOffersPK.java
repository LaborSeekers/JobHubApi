package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class FavoriteJobOffersPK implements Serializable {

    private Integer postulante; // Sale como error, pero si compila sin problemas

    private Integer jobOffer;
}
