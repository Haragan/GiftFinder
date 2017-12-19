package com.giftsearcher.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "rating")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"gift"})
public class Rating extends Identification {

    private double averageRating;

    private int numberVoter;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gift")
    private Gift gift;
}
