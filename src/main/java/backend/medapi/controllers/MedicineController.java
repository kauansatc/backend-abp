package backend.medapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.services.MedicineService;

@RestController
public class MedicineController {
    @Autowired
    MedicineService medicineService;

    @GetMapping("/medicines")
    public ResponseEntity<?> getAll() {
        var list = medicineService.getAll();
        return ResponseEntity.ok(list);
    }

    // @PostMapping("/medicines")
    // public ResponseEntity<?> create() {
    // return ResponseEntity.ok("Medicine created");
    // }
}
