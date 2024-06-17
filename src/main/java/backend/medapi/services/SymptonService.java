package backend.medapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.medapi.models.Sympton;
import backend.medapi.repositories.SymptonRepo;
import jakarta.validation.constraints.NotBlank;

@Service
public class SymptonService {
    @Autowired
    SymptonRepo symptonRepo;

    public List<Sympton> getAll() {
        return symptonRepo.findAll();
    }

    public void delete(@NotBlank String name) {
        var sympton = symptonRepo.findByName(name);
        if (sympton == null)
            throw new RuntimeException("Sympton not found");
        symptonRepo.delete(sympton);
    }
}