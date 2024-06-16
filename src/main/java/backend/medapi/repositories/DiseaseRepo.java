package backend.medapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Disease;

public interface DiseaseRepo extends JpaRepository<Disease, UUID> {
    Disease findByName(String name);
}
