package backend.medapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.dtos.NewMedicineDto;
import backend.medapi.services.MedicineService;
import jakarta.validation.Valid;

@RestController
public class MedicineController {
    @Autowired
    MedicineService medicineService;

    @GetMapping("/medicines")
    public ResponseEntity<?> getAll() {
        var list = medicineService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping("/medicines")
    public ResponseEntity<?> create(@RequestBody @Valid NewMedicineDto newMedicineDto) {
        try {
            medicineService.create(newMedicineDto);
            return ResponseEntity.ok("Medicine created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/medicines/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        try {
            medicineService.delete(name);
            return ResponseEntity.ok("Medicine deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/medicines")
    public ResponseEntity<?> update(@RequestBody @Valid NewMedicineDto newMedicineDto) {
        try {
            medicineService.update(newMedicineDto);
            return ResponseEntity.ok("Medicine updated");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
