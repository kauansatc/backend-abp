package backend.medapi.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.dtos.MedicineDto;
import backend.medapi.dtos.MedicineDtoReq;
import backend.medapi.models.Correlation;
import backend.medapi.models.Medicine;
import backend.medapi.models.Sympton;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.MedicineRepo;
import backend.medapi.repositories.SymptonRepo;

@Service
public class MedicineService {
    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    CorrelationRepo correlationRepo;

    @Autowired
    SymptonRepo symptonRepo;

    public ArrayList<MedicineDto> getAll() {
        ArrayList<MedicineDto> res = new ArrayList<>();

        for (Medicine medicine : medicineRepo.findAll()) {
            ArrayList<String> treatsFor = new ArrayList<>();
            var correlations = correlationRepo.findAllByMedicine(medicine.getName());
            for (Correlation cor : correlations) {
                treatsFor.add(cor.getSympton());
            }
            var dto = new MedicineDto(medicine.getName(), treatsFor, medicine.getNeedsPrescription());

            res.add(dto);
        }

        return res;
    }

    public void add(MedicineDtoReq medicineDto) {
        if (medicineRepo.findByName(medicineDto.name()) != null) {
            throw new IllegalArgumentException("Medicine " + medicineDto.name() + " already exists");
        }

        for (var symptonName : medicineDto.treatsFor()) {
            if (symptonRepo.findByName(symptonName) == null) {
                if (medicineDto.handleNew() != null && medicineDto.handleNew()) {
                    var sympton = new Sympton();
                    sympton.setName(symptonName);
                    symptonRepo.save(sympton);

                    var cor = new Correlation();
                    cor.setMedicine(medicineDto.name());
                    cor.setSympton(symptonName);
                    correlationRepo.save(cor);

                    continue;
                }

                throw new IllegalArgumentException("Sympton " + symptonName + " does not exist");
            }
        }

        var medicine = new Medicine();
        medicine.setName(medicineDto.name());
        medicine.setNeedsPrescription(medicineDto.needsPrescription());

        medicineRepo.save(medicine);
    }
}
