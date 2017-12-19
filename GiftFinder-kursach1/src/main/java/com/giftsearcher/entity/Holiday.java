package com.giftsearcher.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "holiday")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"giftList"})
public class Holiday extends Identification {

    private String holidayName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "holiday_gift",
            joinColumns = @JoinColumn(name="id_holiday"),
            inverseJoinColumns = @JoinColumn(name = "id_gift")
    )
    private List<Gift> giftList;
}
