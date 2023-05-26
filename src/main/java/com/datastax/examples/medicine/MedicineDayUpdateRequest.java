package com.datastax.examples.medicine;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineDayUpdateRequest {
    private LocalDate currentDate;
    private boolean taken;
}
