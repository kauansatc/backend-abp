package backend.medapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Correlation;
import backend.medapi.models.Medicine;
import backend.medapi.models.Sympton;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.MedicineRepo;
import backend.medapi.repositories.SymptonRepo;

@Service
public class SymptonService {
    @Autowired
    SymptonRepo symptonRepo;

    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    CorrelationRepo correlationRepo;

    public List<Sympton> getAll() {
        return symptonRepo.findAll();
    }

    public List<Sympton> getAllByMedicine(String medicine) {
        var correlations = correlationRepo.findAllByMedicine(medicine);
        var symptons = new ArrayList<Sympton>();

        for (var cor : correlations) {
            var sympton = symptonRepo.findByName(cor.getSympton());
            symptons.add(sympton);
        }

        return symptons;
    }

    public void delete(String symptonName, boolean force) {
        var sympton = symptonRepo.findByName(symptonName);

        if (sympton == null) {
            throw new IllegalArgumentException("Sympton " + symptonName + " does not exist");
        }

        var correlations = correlationRepo.findAllBySympton(symptonName);
        if (correlations.length == 1) {
            if (force) {
                var medicine = medicineRepo.findByName(correlations[0].getMedicine());
                medicineRepo.delete(medicine);
                correlationRepo.delete(correlations[0]);
            } else {
                throw new IllegalArgumentException(
                        "Deleting sympton " + symptonName
                                + " will delete medicines only associated to it. (Hint: use ?force=true to delete them with the sympton)");
            }
        }

        for (var cor : correlations) {
            correlationRepo.delete(cor);
        }

        symptonRepo.delete(sympton);
    }

}