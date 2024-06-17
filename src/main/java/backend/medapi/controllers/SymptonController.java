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

import backend.medapi.dtos.UpdateMedicineDto;
import backend.medapi.services.MedicineService;
import backend.medapi.services.SymptonService;

@RestController
public class SymptonController {
    @Autowired
    SymptonService symptonService;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/symptons")
    public ResponseEntity<?> getAll() {
        var list = new ArrayList<String>();
        for (var sympton : symptonService.getAll())
            list.add(sympton.getName());

        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/symptons/{name}")
    public ResponseEntity<?> delete(@PathVariable String name,
            @RequestParam(name = "force", required = false) Boolean force) {
        try {
            ArrayList<String> deletedMedicines = new ArrayList<String>();

            for (var medicine : medicineService.getAll()) {
                var treatsFor = medicine.getTreatsFor();
                if (treatsFor.contains(name)) {
                    treatsFor.remove(name);

                    if (treatsFor.isEmpty() && force != null && force) {
                        medicineService.delete(medicine.getName());
                        deletedMedicines.add(medicine.getName());
                        continue;
                    }

                    try {
                        medicineService.update(medicine.getName(), new UpdateMedicineDto(null, treatsFor, null, null));
                    } catch (Exception e) {
                        throw new IllegalMutationQueryException(
                                "Deleting this sympton will cause some medicines to have no symptons to treat. Use the force parameter to delete this medicines too");
                    }
                }
            }

            symptonService.delete(name);
            var response = "Sympton deleted";
            if (!deletedMedicines.isEmpty())
                response += " and " + deletedMedicines.size() + " medicines deleted too";
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
