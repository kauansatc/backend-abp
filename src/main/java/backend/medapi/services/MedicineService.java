package backend.medapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.dtos.NewMedicineDto;
import backend.medapi.models.Medicine;
import backend.medapi.repositories.DiseaseRepo;
import backend.medapi.repositories.MedicineRepo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Service
public class MedicineService {
    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    DiseaseRepo diseaseRepo;

    public List<Medicine> getAll() {
        return medicineRepo.findAll();
    }

    public boolean exists(@NotBlank String name) {
        return medicineRepo.findByName(name) != null;
    }

    public void create(@Valid NewMedicineDto newMedicineDto) {
        if (exists(newMedicineDto.name())) {
            throw new IllegalArgumentException("Medicine already exists");
        }

        var medicine = new Medicine();
        medicine.setName(newMedicineDto.name());
        medicine.setNeedsPrescription(newMedicineDto.needsPrescription());

        for (var diseaseName : newMedicineDto.treatsFor()) {
            var disease = diseaseRepo.findByName(diseaseName);
            if (disease != null) {
                medicine.addDisease(disease);
            }

            // TODO handle disease not found
            throw new IllegalArgumentException("Disease not found: " + diseaseName);
        }

        medicineRepo.save(medicine);
    }

    public void delete(String name) {
        var medicine = medicineRepo.findByName(name);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine does not exist");
        }
        medicineRepo.delete(medicine);
    }

    public void update(@Valid NewMedicineDto newMedicineDto) {
        var medicine = medicineRepo.findByName(newMedicineDto.name());
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine does not exist");
        }

        medicineRepo.delete(medicine);
        medicineRepo.save(medicine);
    }
}
