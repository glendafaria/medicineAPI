package com.datastax.examples.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
}
