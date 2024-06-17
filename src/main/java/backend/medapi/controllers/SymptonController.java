package backend.medapi.controllers;

import java.util.ArrayList;

import org.hibernate.query.IllegalMutationQueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.services.MedicineService;
import backend.medapi.services.SymptonService;

@RestController
public class SymptonController {
    @Autowired
    SymptonService symptonService;

    @GetMapping("/symptons")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String medicine) {
        if (medicine == null) {
            var list = symptonService.getAll();
            return ResponseEntity.ok(list);
        }

        return ResponseEntity.ok("Not implemented");
        // try {
        // var list = symptonService.getAll(medicine);
        // return ResponseEntity.ok(list);
        // } catch (IllegalMutationQueryException e) {
        // return ResponseEntity.badRequest().body(e.getMessage());
        // }
    }
}
