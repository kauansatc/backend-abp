package backend.medapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Correlation;
import backend.medapi.models.Medicine;
import backend.medapi.models.Sympton;
import backend.medapi.repositories.CorrelationRepo;
import backend.medapi.repositories.SymptonRepo;

@Service
public class SymptonService {
    @Autowired
    SymptonRepo symptonRepo;

    @Autowired
    CorrelationRepo correlationRepo;

    public List<Sympton> getAll() {
        return symptonRepo.findAll();
    }
    /*
     * public ArrayList<Sympton> getAll(Medicine medicine) {
     * ArrayList<Sympton> symptons = new ArrayList<>();
     * var correlations = correlationRepo.findAllByMedicine(medicine.getName());
     * for (Correlation cor : correlations) {
     * symptons.add(symptonRepo.findByName(cor.getSympton()));
     * }
     * return symptons;
     * }
     */
}