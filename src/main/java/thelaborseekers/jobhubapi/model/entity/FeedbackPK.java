package thelaborseekers.jobhubapi.model.entity;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class FeedbackPK {

    private Integer application;

    private Integer jobOffer;
}
