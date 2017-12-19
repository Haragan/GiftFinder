package com.giftsearcher.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class Identification implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
}
