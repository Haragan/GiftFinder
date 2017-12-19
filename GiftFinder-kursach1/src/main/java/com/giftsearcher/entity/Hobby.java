package com.giftsearcher.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "hobby")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"giftList"})
public class Hobby extends Identification {

    private String hobbyName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "hobby_gift",
            joinColumns = @JoinColumn(name="id_hobby"),
            inverseJoinColumns = @JoinColumn(name = "id_gift")
    )
    private List<Gift> giftList;
}
