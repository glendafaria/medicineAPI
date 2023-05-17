package com.datastax.examples.medicine;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.*;

import java.io.Serializable;

@Table(value = "medicine")
@Data
public class Medicine implements Serializable {

    @PrimaryKey
    private Integer id;

    private String name;

    private Double price;

    private Integer amount;

}
