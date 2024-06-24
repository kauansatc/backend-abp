package backend.medapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.medapi.models.Medicine;

public interface MedicineRepo extends JpaRepository<Medicine, String> {
    Medicine findByName(String name);
}
