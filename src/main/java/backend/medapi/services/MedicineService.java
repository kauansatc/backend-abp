package backend.medapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Medicine;
import backend.medapi.repositories.MedicineRepo;

@Service
public class MedicineService {
    @Autowired
    MedicineRepo medicineRepo;

    public List<Medicine> getAll() {
        return medicineRepo.findAll();
    }
}
