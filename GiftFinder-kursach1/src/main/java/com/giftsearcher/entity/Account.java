package com.giftsearcher.entity;

import com.giftsearcher.entity.enumerate.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="account")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString( exclude = {"favoriteGiftList"})
public class Account extends Identification {

    private String email;

    private String passordHash;

    private String firstName;

    private String lastName;

    private Date age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_favorite_gifts",
            joinColumns = @JoinColumn(name = "id_account"),
            inverseJoinColumns = @JoinColumn(name="id_gift")
    )
    private List<Gift> favoriteGiftList;
}