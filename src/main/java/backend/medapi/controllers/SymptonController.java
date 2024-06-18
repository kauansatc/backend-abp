package backend.medapi.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.medapi.services.SymptonService;

@RestController
public class SymptonController {
    @Autowired
    SymptonService symptonService;

    @GetMapping("/symptons")
    public ResponseEntity<?> getAll(@RequestParam(required = false) String medicine,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        try {
            if (medicine == null) {
                var list = symptonService.getAll(page, size);
                var res = new ArrayList<>();
                for (var sympton : list) {
                    res.add(sympton.getName());
                }
                return ResponseEntity.ok(res);
            }

            var list = symptonService.getAllByMedicine(medicine);
            return ResponseEntity.ok(list);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/symptons/{name}")
    public ResponseEntity<?> delete(@PathVariable String name, @RequestParam(required = false) Boolean force) {
        try {
            symptonService.delete(name, force != null && force);
            return ResponseEntity.ok("Sympton " + name + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
