package backend.medapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
        medicineService.create(newMedicineDto);

        return ResponseEntity.ok("Medicine created");
    }
}
