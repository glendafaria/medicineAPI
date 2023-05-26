package com.datastax.examples.medicine;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineDay {
    private LocalDate date;
    private boolean taken;

    // Getter and setter methods for 'date'
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and setter methods for 'taken'
    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
}