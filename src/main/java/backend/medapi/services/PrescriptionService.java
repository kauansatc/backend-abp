package backend.medapi.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Prescription;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.MedicineRepo;
import backend.medapi.repositories.SymptomRepo;

@Service
public class PrescriptionService {
    @Autowired
    CorrelationRepo correlationRepo;

    @Autowired
    SymptomRepo symptomRepo;

    @Autowired
    MedicineRepo medicineRepo;

    public List<Prescription> getPrescription(List<String> symptoms) {
        if (symptoms.size() == 0)
            throw new IllegalArgumentException("Symptoms cannot be empty");

        ArrayList<Prescription> prescription = new ArrayList<>();
        ArrayList<String> readMedicines = new ArrayList<>();

        for (var symptom : symptoms) {
            if (symptomRepo.findByName(symptom) == null)
                throw new IllegalArgumentException("Symptom " + symptom + " not found");

            var cors = correlationRepo.findAllBySymptom(symptom);
            for (var cor : cors) {
                var medicine = cor.getMedicine();
                if (readMedicines.contains(medicine))
                    continue;
                readMedicines.add(medicine);

                var medicineCors = correlationRepo.findAllByMedicine(medicine);

                ArrayList<String> treatsFor = new ArrayList<>();
                var matches = 0;
                for (var medicineCor : medicineCors) {
                    if (symptoms.contains(medicineCor.getSymptom())) {
                        matches++;
                    }
                    treatsFor.add(medicineCor.getSymptom());
                }
                Double score = (double) 100 * matches / symptoms.size();

                prescription.add(new Prescription(medicine, score, treatsFor.toArray(new String[0])));
            }
        }

        prescription.sort(Comparator.comparing(Prescription::getMatchScore).reversed());
        return prescription;
    }
}
