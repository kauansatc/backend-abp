package backend.medapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Symptom;

public interface SymptomRepo extends JpaRepository<Symptom, String> {
    Symptom findByName(String name);
}
