package com.datastax.examples.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicines")
public class MedicineController {

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping("/{id}")
    public Optional<Medicine> getMedicine(@PathVariable Integer id) {
        return medicineRepository.findById(id);
    }

    @GetMapping()
    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @PostMapping
    public Medicine create(@RequestBody Medicine medicine){
        return medicineRepository.save(medicine);
    }

    @PutMapping("/{id}")
    public Optional<Medicine> updateMedicine(@RequestBody Medicine medicine, @PathVariable Integer id) {
        medicine.setId(id);
        return Optional.of(medicineRepository.save(medicine));
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Integer id) {
        medicineRepository.deleteById(id);
    }

}
