package backend.medapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.dtos.NewMedicineDto;
import backend.medapi.models.Medicine;
import backend.medapi.repositories.MedicineRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class MedicineService {
    @Autowired
    MedicineRepo medicineRepo;

    public List<Medicine> getAll() {
        return medicineRepo.findAll();
    }

    public boolean exists(@NotBlank String name) {
        return medicineRepo.findByName(name) != null;
    }

    public void create(@Valid NewMedicineDto newMedicineDto) {
        var medicine = new Medicine();
        medicine.setName(newMedicineDto.name());
        medicine.setNeedsPrescription(newMedicineDto.needsPrescription());
        medicineRepo.save(medicine);
    }
}
