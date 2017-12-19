package com.giftsearcher.entity;

import com.giftsearcher.entity.enumerate.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="gift")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"shop", "likedAccountList", "hobbyList", "holidayList"})
public class Gift extends Identification {

    private String nameGift;

    private double price;

    @Size(max=1000)
    private String description;

    //Рекомендуемый возраст
    private Byte recommendedAge;

    private int appreciated;

    private String imagePath;

    @Transient
    private byte[] image;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdding;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne(mappedBy = "gift" ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Rating rating;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_shop")
    private Shop shop;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_favorite_gifts",
            joinColumns = @JoinColumn(name="id_gift"),
            inverseJoinColumns = @JoinColumn(name = "id_account")
    )
    private List<Account> likedAccountList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "hobby_gift",
            joinColumns = @JoinColumn(name="id_gift"),
            inverseJoinColumns = @JoinColumn(name = "id_hobby")
    )
    private List<Hobby> hobbyList;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "holiday_gift",
            joinColumns = @JoinColumn(name="id_gift"),
            inverseJoinColumns = @JoinColumn(name = "id_holiday")
    )
    private List<Holiday> holidayList;
}