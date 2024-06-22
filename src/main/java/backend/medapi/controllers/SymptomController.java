package backend.medapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.models.Symptom;
import backend.medapi.services.SymptomService;

@RestController
public class SymptomController {
    @Autowired
    SymptomService symptomService;

    @GetMapping("/symptoms")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String medicine,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            List<Symptom> list = new ArrayList<>();

            if (medicine == null) {
                list = symptomService.getAll(page, size);
            } else {
                list = symptomService.getAllByMedicine(medicine);
            }

            var res = new ArrayList<>();
            for (var sympton : list) {
                res.add(sympton.getName());
            }

            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/symptoms/{name}")
    public ResponseEntity<?> update(@PathVariable String name, @RequestParam(required = true) String newName) {
        try {
            symptomService.update(name, newName);
            return ResponseEntity.ok("Sympton " + name + " updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/symptoms/{name}")
    public ResponseEntity<?> delete(@PathVariable String name, @RequestParam(required = false) Boolean force) {
        try {
            symptomService.delete(name, force != null && force);
            return ResponseEntity.ok("Sympton " + name + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
