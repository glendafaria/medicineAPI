package com.datastax.examples.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/{id}")
    public Optional<Medicine> getMedicine(@PathVariable UUID id) {
        return medicineRepository.findById(id);
    }

    @GetMapping()
    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @PostMapping
    public Medicine create(@RequestBody Medicine medicine) {
        UUID id = UUID.randomUUID();
        medicine.setId(id);
        medicine.setDateTime(LocalDateTime.now());
        return medicineRepository.save(medicine);
    }

    @PutMapping("/{id}")
    public Optional<Medicine> updateMedicine(@RequestBody Medicine medicine, @PathVariable UUID id) {
        medicine.setId(id);
        medicine.setDateTime(LocalDateTime.now());
        return Optional.of(medicineRepository.save(medicine));
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable UUID id) {
        medicineRepository.deleteById(id);
    }

    private List<MedicineDay> generateMedicineCalendar(Medicine medicine, LocalDate startDate, LocalDate endDate) {
        List<MedicineDay> medicineCalendar = new ArrayList<>();
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            MedicineDay medicineDay = new MedicineDay();
            medicineDay.setDate(currentDate);
            medicineDay.setTaken(false); // Set initial value to false
            medicineCalendar.add(medicineDay);
            currentDate = currentDate.plusDays(1);
        }
        return medicineCalendar;
    }

    private void updateMedicineDayStatus(Medicine medicine, LocalDate currentDate, boolean taken) {
        List<MedicineDay> medicineCalendar = medicine.getMedicineCalendar();
        for (MedicineDay medicineDay : medicineCalendar) {
            if (medicineDay.getDate().isEqual(currentDate)) {
                medicineDay.setTaken(taken);
                break;
            }
        }
    }

    @GetMapping("/{id}/calendar")
    public ResponseEntity<List<MedicineDay>> getMedicineCalendar(
            @PathVariable UUID id,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
        if (optionalMedicine.isPresent()) {
            Medicine medicine = optionalMedicine.get();
            List<MedicineDay> medicineCalendar = generateMedicineCalendar(medicine, startDate, endDate);
            return ResponseEntity.ok(medicineCalendar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/calendar")
    public ResponseEntity<String> updateMedicineDay(
            @PathVariable UUID id,
            @RequestBody MedicineDayUpdateRequest request) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
        if (optionalMedicine.isPresent()) {
            Medicine medicine = optionalMedicine.get();
            LocalDate currentDate = request.getCurrentDate();
            boolean taken = request.isTaken();
            updateMedicineDayStatus(medicine, currentDate, taken);
            medicineRepository.save(medicine);
            return ResponseEntity.ok("Medicine day updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
