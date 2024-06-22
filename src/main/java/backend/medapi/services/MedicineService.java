package backend.medapi.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import backend.medapi.dtos.MedicineDto;
import backend.medapi.dtos.MedicineDtoOpt;
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

    public ArrayList<MedicineDto> getAll(Integer pageNum, Integer size) {
        ArrayList<MedicineDto> res = new ArrayList<>();

        Pageable pageable = PageRequest.of(pageNum, size);
        Page<Medicine> page = medicineRepo.findAll(pageable);

        for (Medicine medicine : page) {
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

    public void add(MedicineDto medicineDto, Boolean handleNew) {
        if (medicineRepo.findByName(medicineDto.name()) != null) {
            throw new IllegalArgumentException("Medicine " + medicineDto.name() + " already exists");
        }

        for (var symptonName : medicineDto.treatsFor()) {
            if (symptonRepo.findByName(symptonName) == null) {
                if (handleNew == null || !handleNew) {
                    throw new IllegalArgumentException("Sympton " + symptonName + " does not exist");
                }

                var sympton = new Sympton();
                sympton.setName(symptonName);
                symptonRepo.save(sympton);
            }

            var cor = new Correlation();
            cor.setMedicine(medicineDto.name());
            cor.setSympton(symptonName);
            correlationRepo.save(cor);
        }

        var medicine = new Medicine();
        medicine.setName(medicineDto.name());
        medicine.setNeedsPrescription(medicineDto.needsPrescription());

        medicineRepo.save(medicine);
    }

    public void delete(String name) {
        var medicine = medicineRepo.findByName(name);

        if (medicine == null) {
            throw new IllegalArgumentException("Medicine " + name + " does not exist");
        }

        for (var cor : correlationRepo.findAllByMedicine(name)) {
            correlationRepo.delete(cor);
        }

        medicineRepo.delete(medicine);
    }

    public void update(String name, MedicineDtoOpt medicineDto, Boolean handleNew) {
        var medicine = medicineRepo.findByName(name);
        if (medicine == null) {
            throw new IllegalArgumentException("Medicine " + name + " does not exist");
        }

        if (medicineDto.name() != null) {
            var newMedicine = medicineRepo.findByName(medicineDto.name());
            if (newMedicine != null) {
                throw new IllegalArgumentException("Medicine " + medicineDto.name() + " already exists");
            }

            medicine.setName(medicineDto.name());
        }

        if (medicineDto.treatsFor() != null) {
            for (var symptonName : medicineDto.treatsFor()) {
                if (symptonRepo.findByName(symptonName) == null) {
                    if (handleNew == null || !handleNew) {
                        throw new IllegalArgumentException("Sympton " + symptonName + " does not exist");
                    }

                    var sympton = new Sympton();
                    sympton.setName(symptonName);
                    symptonRepo.save(sympton);
                }
            }

            for (var cor : correlationRepo.findAllByMedicine(medicine.getName())) {
                correlationRepo.delete(cor);
            }

            for (var symptonName : medicineDto.treatsFor()) {
                var cor = new Correlation();
                cor.setMedicine(medicine.getName());
                cor.setSympton(symptonName);
                correlationRepo.save(cor);
            }
        }

        if (medicineDto.needsPrescription() != null) {
            medicine.setNeedsPrescription(medicineDto.needsPrescription());
        }

        medicineRepo.save(medicine);
    }
}
