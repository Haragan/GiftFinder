package com.giftsearcher.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shop")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"giftList", "addressList"})
public class Shop extends Identification {

    private String shopName;

//    Изображение логотипа
//    private String logoPath;
//    Изображение отображающейся метки на карте
//    private String labelPath;

    @OneToMany(mappedBy = "shop",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addressList;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gift> giftList;
}
