package com.datastax.examples.medicine;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.LocalDateTime;
import java.util.UUID;

import java.io.Serializable;

@Table(value = "medicine_table")
@Data
public class Medicine implements Serializable {

    @PrimaryKey
    private UUID id;

    private Integer dose;

    private String frequency;

    private String unity;

    private String name;

    private Boolean remind;

    private String time;

    private LocalDateTime dateTime;

}
