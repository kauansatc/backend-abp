package backend.medapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.dtos.NewMedicineDto;
import backend.medapi.dtos.UpdateMedicineDto;
import backend.medapi.models.Sympton;
import backend.medapi.models.Medicine;
import backend.medapi.repositories.SymptonRepo;
import backend.medapi.repositories.MedicineRepo;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class MedicineService {
    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    SymptonRepo symptonRepo;

    private void AssureSymptonExists(String name, @Nullable Boolean registerIfNew) {
        if (name.isEmpty())
            throw new IllegalArgumentException("Sympton name cannot be empty");

        var sympton = symptonRepo.findByName(name);
        if (sympton == null) {
            if (registerIfNew != null && registerIfNew) {
                sympton = new Sympton();
                sympton.setName(name);
                symptonRepo.save(sympton);
            } else {
                throw new IllegalArgumentException("Sympton not found: " + name);
            }
        }
    }

    public List<Medicine> getAll() {
        return medicineRepo.findAll();
    }

    public boolean exists(@NotBlank String name) {
        return medicineRepo.findByName(name) != null;
    }

    public void create(@Valid NewMedicineDto newDto) {
        if (exists(newDto.name())) {
            throw new IllegalArgumentException("Medicine already exists");
        }

        var medicine = new Medicine();
        medicine.setName(newDto.name());
        medicine.setNeedsPrescription(newDto.needsPrescription());

        for (String symptonName : newDto.treatsFor()) {
            AssureSymptonExists(symptonName, newDto.registerNewSymptons());
        }
        medicine.setTreatsFor(newDto.treatsFor());

        medicineRepo.save(medicine);
    }

    public void delete(String name) {
        var medicine = medicineRepo.findByName(name);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine does not exist");
        }
        medicineRepo.delete(medicine);
    }

    public void update(String name, @Valid UpdateMedicineDto updateDto) {
        var medicine = medicineRepo.findByName(name);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine does not exist");
        }

        if (updateDto.name() != null && !updateDto.name().equals(name)) {
            medicine.setName(updateDto.name());
            if (exists(updateDto.name())) {
                throw new IllegalArgumentException("Medicine already exists");
            }
            if (updateDto.name().isEmpty()) {
                throw new IllegalArgumentException("Medicine name cannot be empty");
            }
        }

        if (updateDto.needsPrescription() != null)
            medicine.setNeedsPrescription(updateDto.needsPrescription());

        if (updateDto.treatsFor() != null) {
            if (updateDto.treatsFor().size() == 0) {
                throw new IllegalArgumentException("Medicine must treat at least one sympton");
            }
            for (String symptonName : updateDto.treatsFor()) {
                AssureSymptonExists(symptonName, updateDto.registerNewSymptons());
            }
            medicine.setTreatsFor(updateDto.treatsFor());
        }

        medicineRepo.save(medicine);
    }
}
