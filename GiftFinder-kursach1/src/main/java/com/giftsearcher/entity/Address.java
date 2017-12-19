package com.giftsearcher.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends Identification {

    private String address;

    @Embedded
    private GeoData geoData;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_shop")
    private Shop shop;
}

