package backend.medapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Sympton;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.MedicineRepo;
import backend.medapi.repositories.SymptonRepo;
import org.springframework.data.domain.*;

@Service
public class SymptonService {
    @Autowired
    SymptonRepo symptonRepo;

    @Autowired
    MedicineRepo medicineRepo;

    @Autowired
    CorrelationRepo correlationRepo;

    public List<Sympton> getAll(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Sympton> page = symptonRepo.findAll(pageable);
        return page.getContent();
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
                throw new IllegalArgumentException("Some medicines are exclusively treating this sympton: ["
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
        symptonRepo.delete(sympton);
    }

    public void update(String name, String newName) {
        if (symptonRepo.findByName(newName) != null)
            throw new IllegalArgumentException("Sympton " + newName + " already exists.");

        var sympton = symptonRepo.findByName(name);

        var newSympton = new Sympton();
        newSympton.setName(newName);

        for (var cor : correlationRepo.findAllBySympton(name)) {
            cor.setSympton(newName);
            correlationRepo.save(cor);
        }

        symptonRepo.delete(sympton);
        symptonRepo.save(newSympton);
    }
}