package backend.medapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
        if (medicineService.exists(newMedicineDto.name())) {
            return ResponseEntity.badRequest().body("Medicine already exists");
        }

        medicineService.create(newMedicineDto);
        return ResponseEntity.ok("Medicine created");
    }

    @DeleteMapping("/medicines/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        if (!medicineService.exists(name)) {
            return ResponseEntity.badRequest().body("Medicine does not exist");
        }

        if (!medicineService.delete(name)) {
            return ResponseEntity.badRequest().body("Failed to delete medicine");
        }

        return ResponseEntity.ok("Medicine deleted");
    }
}
