package backend.medapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Symptom;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.MedicineRepo;
import backend.medapi.repositories.SymptomRepo;
import org.springframework.data.domain.*;

@Service
public class SymptomService {
    @Autowired
    SymptomRepo symptomRepo;

    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    CorrelationRepo correlationRepo;

    public List<Symptom> getAll(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Symptom> page = symptomRepo.findAll(pageable);
        return page.getContent();
    }

    public List<Symptom> getAllByMedicine(String medicine) {
        var correlations = correlationRepo.findAllByMedicine(medicine);
        var symptoms = new ArrayList<Symptom>();

        for (var cor : correlations) {
            var symptom = symptomRepo.findByName(cor.getSymptom());
            symptoms.add(symptom);
        }

        return symptoms;
    }

    public void delete(String symptomName, boolean force) {
        var symptom = symptomRepo.findByName(symptomName);

        if (symptom == null) {
            throw new IllegalArgumentException("Symptom " + symptomName + " does not exist");
        }

        var correlations = correlationRepo.findAllBySymptom(symptomName);
        ArrayList<String> exclusevelyCorrelated = new ArrayList<>();

        // boolean isOnlyCorrelated = false;
        for (var cor : correlations) {
            var otherCorrelations = correlationRepo.findAllByMedicine(cor.getMedicine());
            if (otherCorrelations.length == 1) {
                exclusevelyCorrelated.add(cor.getMedicine());
                continue;
            }
        }

        if (exclusevelyCorrelated.size() != 0) {
            if (!force) {
                throw new IllegalArgumentException("Some medicines are exclusively treating this symptom: ["
                        + String.join(", ", exclusevelyCorrelated) + "].\nUse ?force=true to delete anyway.");
            }

            for (var medicineName : exclusevelyCorrelated) {
                var medicine = medicineRepo.findByName(medicineName);
                medicineRepo.delete(medicine);
            }
        }

        for (var cor : correlations) {
            correlationRepo.delete(cor);
        }
        symptomRepo.delete(symptom);
    }

    public void update(String name, String newName) {
        if (symptomRepo.findByName(name) == null)
            throw new IllegalArgumentException("Symptom " + name + " does not exist.");

        if (symptomRepo.findByName(newName) != null)
            throw new IllegalArgumentException("Symptom " + newName + " already exists.");

        var symptom = symptomRepo.findByName(name);

        var newSymptom = new Symptom();
        newSymptom.setName(newName);

        for (var cor : correlationRepo.findAllBySymptom(name)) {
            cor.setSymptom(newName);
            correlationRepo.save(cor);
        }

        symptomRepo.delete(symptom);
        symptomRepo.save(newSymptom);
    }
}