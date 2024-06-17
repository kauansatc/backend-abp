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

import backend.medapi.dtos.MedicineDtoOpt;
import backend.medapi.dtos.MedicineDtoReq;
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

    @PostMapping("/medicines")
    public ResponseEntity<?> add(@RequestBody MedicineDtoReq body) {
        try {
            medicineService.add(body);
            return ResponseEntity.ok("Medicine added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/medicines/{name}")
    public ResponseEntity<?> delete(@PathVariable String name) {
        try {
            medicineService.delete(name);
            return ResponseEntity.ok("Medicine deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/medicines/{name}")
    public ResponseEntity<?> update(@PathVariable String name, @RequestBody MedicineDtoOpt body) {
        try {
            medicineService.update(name, body);
            return ResponseEntity.ok("Medicine updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
