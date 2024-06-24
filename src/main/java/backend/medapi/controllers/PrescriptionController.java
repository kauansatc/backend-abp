package backend.medapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.services.PrescriptionService;

@RestController
public class PrescriptionController {
    @Autowired
    PrescriptionService prescriptionService;

    @GetMapping("/prescription")
    public ResponseEntity<?> getPrescription(@RequestParam(required = true) List<String> symptoms) {
        try {
            var prescription = prescriptionService.getPrescription(symptoms);
            return ResponseEntity.ok(prescription);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
