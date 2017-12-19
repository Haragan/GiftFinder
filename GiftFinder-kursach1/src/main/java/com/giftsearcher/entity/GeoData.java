package com.giftsearcher.entity;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
public class GeoData {

    //Широта
    private double latitude;

    //Долгота
    private double longitude;
}
